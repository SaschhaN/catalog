package org.agile.catalog.data;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> searchByKeywords(List<String> keywords);
}
