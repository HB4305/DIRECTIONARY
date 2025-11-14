# Ứng Dụng Từ Điển Tiếng Lóng (Slang Dictionary Application)


## 🏗️ Cấu Trúc Dự Án

```
DICTIONARY/
├── Main.java                          
├── README.md                        
├── data/
│   ├── slang.txt                      # Dữ liệu từ lóng chính
│   └── slang_of_the_day.txt          # Từ lóng của ngày
├── models/
│   └── SlangEntry.java                # Model đại diện cho một mục từ lóng
├── services/
│   ├── bussiness/                     # Lớp business logic
│   │   ├── SlangDictionary.java       # Quản lý từ điển
│   │   ├── Quiz.java                  # Logic trò chơi quiz
│   │   ├── SlangOfTheDayServices.java # Dịch vụ từ lóng của ngày
│   │   ├── SlangParser.java           # Parser dữ liệu từ lóng
│   │   ├── Factory.java               # Factory pattern
│   │   └── IParsable.java             # Interface cho parser
│   └── dataaccess/                    # Lớp truy cập dữ liệu
│       ├── IDao.java                  # Interface DAO
│       └── TextDao.java               # Triển khai DAO cho file text
└── ui/
    └── Mainapp.java                   # Ứng dụng giao diện chính (JavaFX)
```



## 🚀 Cách Chạy Ứng Dụng

### 1. Chạy từ Source Code


```bash
javac Main.java
java Main
```

### 2. Chạy từ JAR File

```bash
java --module-path "C:\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media,javafx.graphics,jdk.jsobject,jfx.incubator.input,jfx.incubator.richtext --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics -cp DICTIONARY.jar Main
```




