import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String FILE_NAME = "output.csv";

    public static void main(String[] args) {
        System.out.println("Исходный массив данных");
        AppData data = new AppData(new String[]{"Value 1","Value 2","Value 3"},
                new int[][]{ {1,2,3},
                        {4,5,6},
                        {7,8,9} }
        );
        data.print();
        save(data);
        read().print();
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(";"));
    }
    public static void save(AppData data){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(convertToCSV(data.getHeader())+"\n");
            for ( int[] dataRow : data.getData() )
            {
                writer.write(convertToCSV(Arrays.stream(dataRow).mapToObj(String::valueOf).toArray(String[]::new))+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Файл " + FILE_NAME + " сохранен");

    }
    public static AppData read(){
        List<List<String>> records = new ArrayList<>();
        String[] header = {};
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (header.length == 0) {
                    header = values;
                }
                else {
                    records.add( Arrays.asList(values));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Файл " + FILE_NAME + " прочитан");
        int[][] data = new int[header.length][records.size()];
        for (int i = 0; i < header.length; i++) {
            for (int j = 0; j < records.size(); j++) {
                data[i][j] = Integer.parseInt( records.get(i).get(j).toString() );
            }
        }
        return new AppData(header,data);
    }
}
