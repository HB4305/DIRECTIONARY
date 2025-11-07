package services.dataaccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import models.SlangEntry;
import services.bussiness.IParsable;
import services.bussiness.SlangParser;


public class TextDao implements IDao<SlangEntry> {
    private final String sourceFilePath = "data/slang.txt";  // Đường dẫn file .txt
    private final String dataFilePath = "data/slang.dat";    // Đường dẫn file .dat



    /**
     * Lấy toàn bộ dữ liệu slang từ file .txt hoặc .dat (nếu đã có)
     */
    @Override
    public List<SlangEntry> getAll() {
        // Nếu file .dat tồn tại -> đọc object từ đó
        File dataFile = new File(dataFilePath);
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                // Đọc danh sách SlangEntry từ file .dat
                @SuppressWarnings("unchecked")
                List<SlangEntry> entries = (List<SlangEntry>) ois.readObject();
                return entries;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Nếu chưa có .dat -> đọc từ file .txt
        List<SlangEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            reader.readLine(); // Đọc dòng tiêu đề
            while ((line = reader.readLine()) != null) {
                IParsable<SlangEntry> SlangParser = new SlangParser();
                SlangEntry entry = SlangParser.parse(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }

            // Sau khi load xong, lưu sang file .dat để lần sau dùng lại
            saveAll(entries);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

    /**
     * Lưu một slang mới hoặc update lại toàn bộ file .dat
     */
    @Override
    public void save(SlangEntry entity) {
        List<SlangEntry> entries = getAll();
        entries.add(entity);
        saveAll(entries);
    }

    /**
     * Lưu toàn bộ danh sách slang vào file .dat
     */
    @Override
    public void saveAll(List<SlangEntry> entities) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    
}
