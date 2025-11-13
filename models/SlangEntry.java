package models;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

public class SlangEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private String slang;
    private List<String> meanings;

    public SlangEntry(String slang, List<String> meanings) {
        this.slang = slang;
        this.meanings = new ArrayList<>(meanings);
    }

    public String getSlang() {
        return slang;
    }

    public List<String> getMeanings() {
        // Trả về danh sách các nghĩa của từ lóng dùng unmodifiableList để tránh bị thay
        // đổi từ bên ngoài
        return Collections.unmodifiableList(meanings);
    }

    @Override
    // Hàm này dùng để so sánh 2 SlangEntry có cùng slang và cùng meanings hay không
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SlangEntry other = (SlangEntry) obj;
        return slang.equals(other.slang) && meanings.equals(other.meanings);
    }

    @Override
    // Hàm này dùng để tạo mã băm (hash code) cho SlangEntry dựa trên slang
    public int hashCode() {
        return Objects.hash(slang);
    }
}
