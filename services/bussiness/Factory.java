package services.bussiness;
import java.util.HashMap;
import java.util.Map;
public class Factory {
    private static Factory _instance = null;
    private static Map<String, Object> _servicesForType = new HashMap<>();
    
    private Factory() {
        // Private constructor to prevent instantiation
        config();
    };

    private void config() {
        // Initialize services and add them to the _servicesForType map
        _servicesForType.put("SlangDictionary", new SlangDictionary());
    }

    public static Factory getInstance() {
        if (_instance == null) {
            _instance = new Factory();
        }
        return _instance;
    }

    public void registerServiceForType(String type, Object service) {
        _servicesForType.put(type, service);
    }

    public Object getServiceForType(String type) {
        return _servicesForType.get(type);
    }
}
