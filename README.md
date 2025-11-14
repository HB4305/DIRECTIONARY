# Slang Dictionary Application


## Cấu Trúc Dự Án

```
DICTIONARY/
├── Main.java
├── README.md
├── bin/
├── lib/
├── data/
│   ├── slang.txt
│   └── slang_of_the_day.txt
├── models/
│   └── SlangEntry.java
├── services/
│   ├── bussiness/
│   │   ├── SlangDictionary.java
│   │   ├── Quiz.java
│   │   ├── SlangOfTheDayServices.java
│   │   ├── SlangParser.java
│   │   ├── Factory.java
│   │   └── IParsable.java
│   └── dataaccess/
│       ├── IDao.java
│       ├── TextDao.java
│       └── BinaryDao.java
└── ui/
    └── Mainapp.java
```



## Cách Chạy Ứng Dụng

### Terminal:

```bash
java --module-path "lib" --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media,javafx.graphics,jdk.jsobject,jfx.incubator.input,jfx.incubator.richtext --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics -cp DICTIONARY.jar Main
```