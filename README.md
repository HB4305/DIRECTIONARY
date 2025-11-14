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