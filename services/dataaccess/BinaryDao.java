package services.dataaccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import models.SlangEntry;

public class BinaryDao implements IDao<SlangEntry> {
    private final String dataFilePath = "data/slang.dat";

    @Override
    public List<SlangEntry> getAll() {

        try (var ois = new java.io.ObjectInputStream(new java.io.FileInputStream(dataFilePath))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<SlangEntry> entries = (List<SlangEntry>) obj;
                return entries;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return List.of();
    }

    @Override
    public void save(SlangEntry entity) {
        List<SlangEntry> entries = getAll();

        boolean updated = false;
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSlang().equals(entity.getSlang())) {
                entries.set(i, entity);
                updated = true;
                break;
            }
        }
        if (!updated) {
            entries.add(entity);
        }

        saveAll(entries);
    }

    @Override
    public void saveAll(List<SlangEntry> entities) {
        File dataFile = new File(dataFilePath);
        File parentDir = dataFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SlangEntry> resetData() {
        File dataFile = new File(dataFilePath);
        if (dataFile.exists()) {
            dataFile.delete();
        }
        return getAll();
    }

    @Override
    public void delete(SlangEntry entity) {
        List<SlangEntry> entries = getAll();
        entries.removeIf(e -> e.getSlang().equals(entity.getSlang()));
        saveAll(entries);
    }
}
