package org.agile.catalog.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> searchByKeywords(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return List.of();
        }

        // normalize & filter empty tokens
        List<String> cleaned = new ArrayList<>();
        for (String k : keywords) {
            if (k != null) {
                String t = k.trim();
                if (!t.isEmpty()) cleaned.add(t.toLowerCase());
            }
        }
        if (cleaned.isEmpty()) return List.of();


        StringBuilder sb = new StringBuilder("SELECT b FROM Book b WHERE ");

        List<String> clauses = new ArrayList<>();
        for (int i = 0; i < cleaned.size(); i++) {
            String param = ":kw" + i;
            String clause = "("
                    + "LOWER(b.title) LIKE " + param
                    + " OR LOWER(b.description) LIKE " + param
                    + " OR LOWER(b.author) LIKE " + param
                    + ")";
            clauses.add(clause);
        }
        sb.append(String.join(" AND ", clauses));

        TypedQuery<Book> query = em.createQuery(sb.toString(), Book.class);

        // set parameters with wildcards
        for (int i = 0; i < cleaned.size(); i++) {
            query.setParameter("kw" + i, "%" + cleaned.get(i) + "%");
        }

        return query.getResultList();
    }
}
