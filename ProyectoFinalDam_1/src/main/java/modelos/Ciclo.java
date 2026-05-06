/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import Config.appConfig;
import Utils.Validadores;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import servicios.BaseDeDatos.*;
import servicios.Ficheros.GestionFicheros;

/*
 * @author isard
 */
public class Ciclo implements interfaces.interpolaridadDeDatos, Serializable {
    // Aqui va la creacion del objeto Ciclo, el cual deberemos meter en la base de datos 

    private int codigo;
    private String denominacion;
    private String familiaProfesional;
    private String nivel;
    private int horasCiclo;
    private int añoCurricular;
    private HashMap<Integer, Modulo> modulos;

    /**
     * Creador manual de Ciclos desde el programa (Swing)
     *
     * @param denominacion
     * @param familiaProfesional
     * @param nivel
     * @param horasCiclo
     * @param añoCurricular
     */
    public Ciclo(String denominacion, String familiaProfesional, String nivel, int horasCiclo, int añoCurricular) {
        this.codigo = ConsultasEspecificas.leerCodigoBDD("Ciclo");
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.nivel = nivel;
        this.horasCiclo = horasCiclo;
        this.añoCurricular = añoCurricular;
    }

    /**
     * Creador automatico de ciclos desde la BDD
     *
     * @param codigo
     * @param denominacion
     * @param familiaProfesional
     * @param nivel
     * @param horasCiclo
     * @param añoCurricular
     */
    public Ciclo(int codigo, String denominacion, String familiaProfesional, String nivel, int horasCiclo, int añoCurricular) {
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.familiaProfesional = familiaProfesional;
        this.nivel = nivel;
        this.horasCiclo = horasCiclo;
        this.añoCurricular = añoCurricular;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public String getFamiliaProfesional() {
        return familiaProfesional;
    }

    public int getHorasCiclo() {
        return horasCiclo;
    }

    public int getAñoCurricular() {
        return añoCurricular;
    }

    public HashMap<Integer, Modulo> getModulos() {
        return modulos;
    }
    
    public int getCodigo() {
        return codigo;
    }
    public String getNivel() {
        return nivel;
    }

    // ============================================================================================================================================================================
    // ==================== SAVE ==================================================================================================================================================
    // ============================================================================================================================================================================
    /**
     * Guarda los ciclos en un archivo CSV
     */
    @Override
    public void saveToCSV() {
        GestionFicheros.crearFichero(appConfig.ficheroCiclo + ".csv");

        try {
            try ( BufferedWriter bw = new BufferedWriter(new FileWriter(appConfig.ficheroCiclo + ".csv", true))) {
                bw.write(toCSV());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error inesperado " + e);
        }
    }

    /**
     * Guarda los alumnos en un archivo JSON
     */
    @Override
    public void saveToJSON() {
        GestionFicheros.crearFichero(appConfig.ficheroCiclo + ".json");
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(appConfig.ficheroCiclo + ".json", true))) {

            bw.write(toJSON());
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    /**
     * Guarda los ciclos en un archivo binario
     */
    @Override
    public void saveToBinario() {
        GestionFicheros.crearFichero(appConfig.ficheroCiclo + ".dat");
        try ( DataOutputStream dos = new DataOutputStream(new FileOutputStream(appConfig.ficheroCiclo + ".dat", true))) {

            dos.writeInt(codigo);
            dos.writeUTF(denominacion);
            dos.writeUTF(familiaProfesional);
            dos.writeInt(horasCiclo);
            dos.writeInt(añoCurricular);
            dos.close();

        } catch (IOException e) {
            System.out.println("Error al guardar Binario: " + e.getMessage());
        }
    }

    /**
     * Guarda los alumnos en un archivo TXT
     */
    @Override
    public void saveToTXT() {
        GestionFicheros.crearFichero(appConfig.ficheroCiclo + ".txt");

        try ( BufferedWriter bw = new BufferedWriter(
                new FileWriter(appConfig.ficheroCiclo + ".txt", true))) {

            bw.write(toTXT());
            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar TXT: " + e.getMessage());
        }

    }
    
    // ============================================================================================================================================================================
    // ==================== LOAD ==================================================================================================================================================
    // ============================================================================================================================================================================

    /**
     * Convierte de CSV a objeto
     */
    @Override
    public void objFromCSV() {
        if (Utils.Validadores.comprobarFicheroLectura(appConfig.ficheroCiclo, ".csv")) {
            String linea;
            try {
                BufferedReader br = new BufferedReader(new FileReader(appConfig.ficheroCiclo + ".csv"));
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(";");

                    int tempCode = Integer.parseInt(datos[0]);
                    String tempDenominacion = datos[1];
                    String tempFamiliaProfesional = datos[2];
                    String niv = datos[3];
                    int tempHorasCiclo = Integer.parseInt(datos[4]);
                    int tempAñoCurricular = Integer.parseInt(datos[5]);

                    System.out.println("Codigo: " + tempCode);
                    System.out.println("Denominacion: " + tempDenominacion);
                    System.out.println("Familia Profesional: " + tempFamiliaProfesional);
                    System.out.println("Horas Ciclo: " + tempHorasCiclo);
                    System.out.println("Año Curricular: " + tempAñoCurricular);
                    System.out.println("-----------------");

                    Ciclo ciclo = new Ciclo(tempCode, tempDenominacion, tempFamiliaProfesional, niv, tempHorasCiclo, tempAñoCurricular);

                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo CSV: " + e.getMessage());
            }

        }
    }

    // ============================================================================================================================================================================
    // ================= CONVERTIDORES ============================================================================================================================================
    // ============================================================================================================================================================================
    /**
     * Convierte de JSON a objeto
     */
    @Override
    public void objFromJSON() {

        File f = new File(appConfig.ficheroCiclo + ".json");
        Validadores.comprobarFicheroLectura(appConfig.ficheroCiclo, ".json");

        Gson gson = new Gson();
        String linea;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {

                    Ciclo ciclo = gson.fromJson(linea, Ciclo.class);

                    System.out.println("Código: " + ciclo.getCodigo());
                    System.out.println("Denominacion: " + ciclo.getDenominacion());
                    System.out.println("Familia Profesional: " + ciclo.getFamiliaProfesional());
                    System.out.println("Horas Ciclo: " + ciclo.getHorasCiclo());
                    System.out.println("Año Curricular: " + ciclo.getAñoCurricular());
                    System.out.println("-----------------");
                }
            }
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Ha ocurrido un error al crear el objeto desde JSON, error: " + e.getMessage());
        }

    }

    /**
     *
     */
    @Override
    public void objFromBinario() {

        File f = new File(appConfig.ficheroCiclo + ".dat");
        Validadores.comprobarFicheroLectura(appConfig.ficheroCiclo, ".dat");

        try ( DataInputStream dis = new DataInputStream(new FileInputStream(f))) {

            while (dis.available() > 0) {

                int tempCode = dis.readInt();
                String tempNombre = dis.readUTF();
                String tempDenominacion = dis.readUTF();
                String tempFamiliaProfesional = dis.readUTF();
                String niv = dis.readUTF();
                int tempHorasCiclo = dis.readInt();
                int tempAñoCurricular = dis.readInt();

                Ciclo ciclo = new Ciclo(
                        tempCode,
                        tempDenominacion,
                        tempFamiliaProfesional,
                        niv,
                        tempHorasCiclo,
                        tempAñoCurricular
                );

                System.out.println("Código: " + ciclo.getCodigo());
                System.out.println("Denominacion: " + ciclo.getDenominacion());
                System.out.println("Familia Profesional: " + ciclo.getFamiliaProfesional());
                System.out.println("Horas Ciclo: " + ciclo.getHorasCiclo());
                System.out.println("Año Curricular: " + ciclo.getAñoCurricular());
                System.out.println("-----------------");
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error al crear el objeto desde binario, error: " + ex.getMessage());
        }
    }

    /**
     *
     */
    @Override
    public void objFromTXT() {
        File f = new File(appConfig.ficheroCiclo + ".txt");
        Validadores.comprobarFicheroLectura(appConfig.ficheroCiclo, ".txt");

        try ( BufferedReader br = new BufferedReader(new FileReader(f))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(";");

                int tempCode = Integer.parseInt(datos[0]);
                String tempDenominacion = datos[1];
                String tempFamiliaProfesional = datos[2];
                String niv = datos[3];
                int tempHorasCiclo = Integer.parseInt(datos[4]);
                int tempAñoCurricular = Integer.parseInt(datos[5]);

                 Ciclo ciclo = new Ciclo(
                        tempCode,
                        tempDenominacion,
                        tempFamiliaProfesional,
                        niv,
                        tempHorasCiclo,
                        tempAñoCurricular
                );

                System.out.println("Código: " + ciclo.getCodigo());
                System.out.println("Denominacion: " + ciclo.getDenominacion());
                System.out.println("Familia Profesional: " + ciclo.getFamiliaProfesional());
                System.out.println("Horas Ciclo: " + ciclo.getHorasCiclo());
                System.out.println("Año Curricular: " + ciclo.getAñoCurricular());
                System.out.println("-----------------");
            }

        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error al crear el objeto desde el fichero de texto, error: " + ex.getMessage());;
        }

    }

    // ============================================================================================================================================================================
    // ===================== SQL ==================================================================================================================================================
    // ============================================================================================================================================================================
    
    /**
     *
     * @return
     */
    public String SqlToObj() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String ObjToSql() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Convierte la informacion a un String csv
     *
     * @return
     */
    @Override
    public String toCSV() {
        return codigo + ";" + denominacion + ";" + familiaProfesional + ";" + nivel + ";" + horasCiclo + ";" + añoCurricular;
    }

    /**
     * Convierte la informacion a un String json
     *
     * @return
     */
    @Override
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Convierte la informacion a un String TXT
     *
     * @return
     */
    @Override
    public String toTXT() {
        return toCSV();
    }
    //Metodos de la clase

    public void agregarModulo(Modulo modulo) {
        this.modulos.put(modulo.getCodigo(),modulo);
    }


    // ============================================================================================================================================================================
    // ===================== TO STRING ============================================================================================================================================
    // ============================================================================================================================================================================ 
   
    

    

}
