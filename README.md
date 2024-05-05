### Instalasi ###
1. Clone repository dengan menjalankan perintah
```
git clone https://github.com/aininurulazizah/Test-Automation.git
```
2. Buka proyek menggunakan IDE pemrograman. Kami menyarankan menggunakan IntelliJ
3. Proses instalasi JavaBuild akan dilakukan secara otomatis ketika proyek dibuka dan tunggu hingga proses instalasi selesai.

### Struktur Project ###
Tujuan proyek ini adalah untuk melakukan pengujian API pada web DummyAPI secara otomatis. Kode program untuk melakukan pengujian tersimpan dalam direktori `test`. Berikut adalah struktur proyek secara keseluruhan.
```
.
└── src
    ├── main
    └── test
        ├── java
        │   ├── APITest.java
        │   └── Main.java
        ├── resources
        │   ├── create-schema.json
        │   ├── schema.json
        │   └── schema_id_only.json
        └── schema.json
```
- `APITest.java` berisi kumpulan script tes otomatis untuk menguji API yang tersedia pada DummyAPI.
- `create-schema.json` skema JSON yang digunakan untuk memvalidasi respons JSON dari permintaan POST.
- `schema.json` skema JSON yang digunakan untuk memvalidasi data yang diterima dalam format JSON.
- `schema_id_only.json` memvalidasi respons JSON yang hanya mengandung properti id.

### Referensi ###
- [Maven Documentation](https://maven.apache.org/guides/index.html)
- [TestNg Documentation](https://www.javadoc.io/doc/org.testng/testng/6.8.17/org/testng/Assert.html)
- [Rest Assured Documentation](https://rest-assured.io/)
- [Rest Assured Tutorial](https://www.toolsqa.com/rest-assured-)
- [Rest Assured Video Tutorial](https://www.youtube.com/watch?v=Orn8cP1yRJc)
