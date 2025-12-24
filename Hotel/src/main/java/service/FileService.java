package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public interface FileService<T> {

    File checkFile(String fileName);
    void exportToFile(String fileName, List<T> list);
    void importFromFile(String fileName);
}
