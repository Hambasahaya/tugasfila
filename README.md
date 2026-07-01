# Sistem Data Mahasiswa

Mini project SIAKAD dengan Java Spring Boot, Thymeleaf, Bootstrap, dan MySQL.

## Fitur

- Login dengan Spring Security
- Dashboard statistik
- CRUD Mahasiswa
- CRUD Dosen
- Pencarian data
- Pagination
- Tampilan responsive Bootstrap
- Database MySQL dengan JPA/Hibernate

## Akun Demo

- Username: `admin`
- Password: `admin123`

## Menjalankan

1. Pastikan MySQL aktif.
2. Sesuaikan `spring.datasource.username` dan `spring.datasource.password` di `src/main/resources/application.properties`.
3. Jalankan aplikasi:

```bash
mvn spring-boot:run
```

4. Buka `http://localhost:8080`.

Database `siakad_db` akan dibuat otomatis jika user MySQL memiliki izin membuat database.
