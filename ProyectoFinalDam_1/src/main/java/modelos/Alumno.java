package modelos;

import Config.appConfig;
import Utils.Validadores;
import com.google.gson.Gson;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.Insert;
import servicios.Ficheros.GestionFicheros;

public class Alumno implements interfaces.interpolaridadDeDatos {

    // =========================================================
    // ===================== ATRIBUTOS =========================
    // =========================================================

    private final int codigo;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String domicilio;
    private String telefono;
    private String correo;

    private HashSet<Matricula> matriculas;

    // =========================================================
    // =================== CONSTRUCTORES =======================
    // =========================================================

    /**
     * Creación manual (nuevo alumno)
     * @param nombre
     */
    public Alumno(String nombre,
                  LocalDate fechaNacimiento,
                  String domicilio,
                  String telefono,
                  String correo) {

        this.codigo = ConsultasEspecificas.leerCodigoBDD("alumno");
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;

        this.matriculas = new HashSet<>();
    }

    /**
     * Creación desde base de datos
     */
    public Alumno(int codigo,
                  String nombre,
                  LocalDate fechaNacimiento,
                  String domicilio,
                  String telefono,
                  String correo) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.correo = correo;

        this.matriculas = new HashSet<>();
    }

    // =========================================================
    // ===================== GETTERS ===========================
    // =========================================================

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public HashSet<Matricula> getMatriculas() {
        return matriculas;
    }

    public void agregarMatriculas(Matricula m) {
        this.matriculas.add(m);
    }

    // =========================================================
    // ===================== SAVE TO ===========================
    // =========================================================

    @Override
    public void saveToCSV() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".csv");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroAlumno + ".csv", true))) {

            bw.write(toCSV());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error CSV: " + e.getMessage());
        }
    }

    @Override
    public void saveToJSON() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".json");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroAlumno + ".json", true))) {

            bw.write(toJSON());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error JSON: " + e.getMessage());
        }
    }

    @Override
    public void saveToBinario() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".dat");

        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(appConfig.ficheroAlumno + ".dat", true))) {

            dos.writeInt(codigo);
            dos.writeUTF(nombre);
            dos.writeUTF(fechaNacimiento.toString());
            dos.writeUTF(domicilio);
            dos.writeUTF(telefono);
            dos.writeUTF(correo);

        } catch (IOException e) {
            System.out.println("Error BIN: " + e.getMessage());
        }
    }

    @Override
    public void saveToTXT() {
        GestionFicheros.crearFichero(appConfig.ficheroAlumno + ".txt");

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroAlumno + ".txt", true))) {

            bw.write(toTXT());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error TXT: " + e.getMessage());
        }
    }

    // =========================================================
    // =================== FROM FILES ==========================
    // =========================================================

    @Override
    public void objFromCSV() {

        if (Validadores.comprobarFichero(appConfig.ficheroAlumno, ".csv")) {

            try (BufferedReader br = new BufferedReader(
                    new FileReader(appConfig.ficheroAlumno + ".csv"))) {

                String linea;

                while ((linea = br.readLine()) != null) {

                    String[] d = linea.split(";");

                    Alumno a = new Alumno(
                            Integer.parseInt(d[0]),
                            d[1],
                            LocalDate.parse(d[2]),
                            d[3],
                            d[4],
                            d[5]
                    );

                    System.out.println(a);
                    System.out.println("-----------------");
                }

            } catch (IOException e) {
                System.out.println("Error CSV");
            }
        }
    }

    @Override
    public void objFromJSON() {

        File f = new File(appConfig.ficheroAlumno + ".json");

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (!linea.isBlank()) {
                    Alumno a = new Gson().fromJson(linea, Alumno.class);

                    System.out.println(a);
                    System.out.println("-----------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Error JSON: " + e.getMessage());
        }
    }

    @Override
    public void objFromBinario() {

        File f = new File(appConfig.ficheroAlumno + ".dat");

        try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {

            while (dis.available() > 0) {

                Alumno a = new Alumno(
                        dis.readInt(),
                        dis.readUTF(),
                        LocalDate.parse(dis.readUTF()),
                        dis.readUTF(),
                        dis.readUTF(),
                        dis.readUTF()
                );

                System.out.println(a);
                System.out.println("-----------------");
            }

        } catch (IOException e) {
            System.out.println("Error BIN: " + e.getMessage());
        }
    }

    @Override
    public void objFromTXT() {

        File f = new File(appConfig.ficheroAlumno + ".txt");

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] d = linea.split(";");

                Alumno a = new Alumno(
                        Integer.parseInt(d[0]),
                        d[1],
                        LocalDate.parse(d[2]),
                        d[3],
                        d[4],
                        d[5]
                );

                System.out.println(a);
                System.out.println("-----------------");
            }

        } catch (IOException e) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // =========================================================
    // ===================== SQL ===============================
    // =========================================================

    public static Alumno SqlToObj(ResultSet rs) throws SQLException {

        return new Alumno(
                rs.getInt("codigo"),
                rs.getString("nombre"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("domicilio"),
                rs.getString("telefono"),
                rs.getString("correo")
        );
    }

    public void ObjToSql() {
        Insert.insertarAlumno(this);
    }

    // =========================================================
    // ================= CONVERTIDORES =========================
    // =========================================================

    @Override
    public String toCSV() {
        return codigo + ";" + nombre + ";" + fechaNacimiento + ";" +
                domicilio + ";" + telefono + ";" + correo;
    }

    @Override
    public String toJSON() {
        return new Gson().toJson(this);
    }

    @Override
    public String toTXT() {
        return toCSV();
    }

    // =========================================================
    // ===================== TO STRING =========================
    // =========================================================

    @Override
    public String toString() {
        return "Alumno{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", domicilio='" + domicilio + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}