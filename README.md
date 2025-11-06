com.yourname.slangdictionary
│
├── Main.java               // (Lớp khởi chạy duy nhất)
│
├── models                  // 1. Lớp đối tượng (Data)
│   ├── SlangEntry.java
│   ├── Quiz.java
│   └── (Các lớp Quiz con...)
│
├── services                // 2. Lớp nghiệp vụ & dữ liệu
│   ├── business            // 3. (Business Logic Layer)
│   │   └── SlangDictionary.java
│   └── dataaccess          // 4. (Data Access Layer)
│       ├── FileStorageService.java
│       └── TextParserService.java
│
├── ui                      // 5. Lớp giao diện (View)
│   └── ConsoleView.java
│
└── utils                   // 6. Lớp tiện ích (Helpers)
    └── QuizFactory.java