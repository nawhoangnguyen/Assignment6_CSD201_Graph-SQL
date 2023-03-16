package Graph;

import Element.Edge;
import Element.Vertex;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static String[][] adjacencyMatrixWithVertices = null;
    private static String[][] adjacencyMatrix = null;
    private static List<Edge<String>> edgeList = null;
    private static List<Vertex<String>> getVertex = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static List<String> getVertexName = new ArrayList<>();

    //=========================================LOAD FROM FILE=====================================================
//    public static String[][] Load(String file_path) throws FileNotFoundException, IOException {
//        String[][] matrix = null;
////        InputStream stream = ClassLoader.getSystemResourceAsStream(file_path);
////        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
//        BufferedReader buffer = new BufferedReader(new FileReader(file_path));
//        List<String[]> list = new ArrayList<>();
//
//        String line;
//        int row = 0;
//
//        int column = 0;
//        while ((line = buffer.readLine()) != null) {
//            String[] vals = line.split(" ");
//            column = vals.length;
//            list.add(vals);
//            row++;
//        }
//
//        matrix = new String[row][column];
//
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                matrix[i][j] = list.get(i)[j];
//            }
//        }
//
//        return matrix;
//
//    }
    //=================================================================================================================

    static List<Edge<String>> createListOfEdgeOneDirect(String[][] adjMatrix, List<Vertex<String>> listOfVertices) { //For example: BA != AB (We know that AB is BA but in this class AB has direction from A->B 
        List<Edge<String>> listOfEdges = new ArrayList<>();                                                         //  so A is Source and B is Destination ---- and ---- Ba has direction from B->A
        for (int i = 0; i < adjMatrix.length; i++) {                                                                //  so B is Source and A is Destination // Actually it is the same but we want to split them out
            for (int j = 0; j < adjMatrix[i].length; j++) {                                                         //                                          for access two both destination and source
                if (!(adjMatrix[i][j]).equals("0")) {
                    listOfEdges.add(new Edge(listOfVertices.get(i), listOfVertices.get(j), Integer.parseInt(adjMatrix[i][j])));
                }
            }
        }
        return listOfEdges;
    }

    //CREATE noneDoubleEdge using for incidence matrix
    static List<Edge<String>> createListOfEdge(String[][] adjMatrix, List<Vertex<String>> listOfVertices) {
        List<Edge<String>> listOfEdges = new ArrayList<>();
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                if (!(adjMatrix[i][j]).equals("0")) {
                    listOfEdges.add(new Edge(listOfVertices.get(i), listOfVertices.get(j), Integer.parseInt(adjMatrix[i][j])));
                }
            }
        }

        for (int i = 0; i < listOfEdges.size(); i++) {
            for (int j = i + 1; j < listOfEdges.size(); j++) {
                if ((listOfEdges.get(i).getSource().getData().equals(listOfEdges.get(j).getDestination().getData())) && (listOfEdges.get(i).getDestination().getData().equals(listOfEdges.get(j).getSource().getData()))) {
                    listOfEdges.remove(listOfEdges.get(j));
                }
            }
        }

        return listOfEdges;
    }

    //====================================LOAD FROM DATA BASE=====================================
    //====LOADING EFFECT
    public static void printMsgWithProgressBar(String message, int length, long timeInterval) throws Exception {
        char incomplete = '░';
        char complete = '█';
        System.out.println(message);

        for (int i = 0; i <= length; i++) {
            for (int j = 0; j < length; j++) {
                if (j < i) {
                    System.out.print(complete);
                } else {
                    System.out.print(incomplete);
                }
            }
            System.out.print("\r");
            Thread.sleep(timeInterval);
        }
    }
    //====
    
    //LOAD FROM DATABASE
        //FILE DATABASE ĐÃ CÓ SẴN TRONG FILE RÙI Ạ TÊN Data.sql
    public static void LoadDatabase() {

        DBContext db = new DBContext();
        int NumOfCol = 0;
        int NumOfRow = 0;

        List<List<String>> container = new ArrayList<>();
        List<String> nameVertex = new ArrayList<>();

        try {
            System.out.println("-----------RETRIEVING DATA FROM SQL SERVER----------");
            System.out.println("");

            // =======================================
            try {
                printMsgWithProgressBar("Connection established......\n", 69, 25);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            // ========================================
            String query = "SELECT * FROM AdjacencyMatrix ";//Create query
            Connection connect = db.getConnection(); //Connect to SQL server
            ps = connect.prepareStatement(query);
            rs = ps.executeQuery();//Execute query and save the data to ResultSet;
            ResultSetMetaData rsmd = rs.getMetaData();

            NumOfCol = rsmd.getColumnCount();//Take column size
            // Take all the data from adjacency matrix in database, if null then stop while
            while (rs.next()) {
                List<String> rows = new ArrayList<>();
                for (int i = 1; i <= 8; i++) {
                    rows.add(rs.getString(i)); //Add data to rows ArrayList
                }
                container.add(rows); //Add rows arraylist to container arraylist
                NumOfRow++;//Take row size 

            }
            System.out.println("");
            System.out.println("");
            System.out.println("                        SUCCESSFULLY!!!!!!                    ");
            System.out.println("");
            System.out.println("");

            String query2 = "SELECT * FROM VertexName";//Create query
            ps = connect.prepareStatement(query2);//Connect to SQL server

            rs = ps.executeQuery(); //Execute query and save the data to ResultSet;
            System.out.print("List of vertex: ");
            // take all the vertex from nameOfVertex in database, if null then stop while
            while (rs.next()) {
                getVertexName.add(rs.getString(1));//Take all the data from the first column in database then add all of it into List<String>getVertexName
                System.out.print(rs.getString(1) + " ");//Then print all the vertex at the first column in Database
//                rs.getString(1) mean get all the values in the first(1) colum

            }

            System.out.println("");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Num of Row: " + NumOfRow);
        System.out.println("Num of Col: " + NumOfCol);

        System.out.println("");

//        Create new 2d matrix to hold the data
        String[][] adjMatrix = new String[NumOfRow][NumOfCol];

        int countRow = 0;
        for (List<String> list : container) {
            int countColumn = 0;
            for (String string : list) {
                adjMatrix[countRow][countColumn] = list.get(countColumn);//list.get(countColumn)
//                      set matrix row and column size                                                           |return the data at specific position in list
                countColumn++;
            }
            countRow++;
        }

        adjacencyMatrix = Arrays.copyOf(adjMatrix, NumOfRow);//Arrays.copyOf(adjMatrix, NumOfRow)
        //       Copy all the data from 2d adjMatrix array to adjacencyMatrix 2d array| Set array size
    }
    //====================================================================================================  

    //-----------------------------------------------------------------------------------------------------
    //=========================================Convert1=====================================================
    static void Convert1() {
        adjMatrixToIncMatrix(getVertex, edgeList);
    }

    static void adjMatrixToIncMatrix(List<Vertex<String>> listVertex, List<Edge<String>> listEdge) {
        System.out.print("  ");
        for (Edge<String> edge : listEdge) {

            System.out.print(" " + edge.getSource() + "" + edge.getDestination());

        }
        System.out.println("");
        for (Vertex<String> vertex : listVertex) {
            System.out.print(vertex.getData() + " ");
            for (Edge<String> edge : listEdge) {
                if (vertex.getData().equals(edge.getSource().getData())
                        || vertex.getData().equals(edge.getDestination().getData())) {

                    System.out.print("  " + edge.getWeight());

                } else {
                    System.out.print("  0");
                }
            }
            System.out.println("");
        }

        System.out.println("");

    }
    //=====================================================================================================

    //-----------------------------------------------------------------------------------------------------
    //=========================================Convert2=====================================================
    static void Convert2() {
        adjMatrixToList(getVertex, edgeList);

    }

    static void adjMatrixToList(List<Vertex<String>> listVertex, List<Edge<String>> listEdge) {

        for (Vertex<String> vertex : listVertex) {
            System.out.print(vertex.getData());

            for (Vertex<String> neighbor : vertex.getNeighbors()) {
                for (Edge<String> edge : listEdge) {
                    if (((vertex.getData().equals((edge.getSource().getData())) && (neighbor.getData().equals(edge.getDestination().getData()))))
                            || ((neighbor.getData().equals((edge.getSource().getData())) && (vertex.getData().equals(edge.getDestination().getData()))))) {
                        System.out.print("  ->  " + neighbor.getData() + "| " + edge.getWeight());
                    }

                }

            }
            System.out.println("");
        }

    }
    //=====================================================================================================

    //-----------------------------------------------------------------------------------------------------
    //=========================================BFS=========================================================
    public static void BFS(String s) {
        System.out.println(
                "Following is Breadth First Traversal "
                + "(starting from vertex " + s + ")");
       LinkedList<Vertex<String>> queue = new LinkedList<>();
        LinkedList<Vertex<String>> visited = new LinkedList<>();

        Vertex<String> newVertex = new Vertex<>(s);

        for (Vertex<String> vertexInList : getVertex) {
            if (vertexInList.getData().equals(s)) {
                newVertex = vertexInList;
                break;
            }
        }

        visited.add(newVertex);
        queue.add(newVertex);

        while (!queue.isEmpty()) {
            
            newVertex = queue.poll(); 
            for (Vertex<String> v : getVertex) {
                
                for (Vertex<String> neighbor : v.getNeighbors()) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }

            }
        }
        for (Vertex<String> v : visited) {
            System.out.println(v.getData());
        }
    }
    //=======================================================================================================

    //-----------------------------------------------------------------------------------------------------
    //===========================================DFS=========================================================
    public static void DFS(String s) {
        System.out.println(
                "Following is Depth First Traversal "
                + "(starting from vertex " + s + ")");
        LinkedList<Vertex<String>> stack = new LinkedList<>();
        LinkedList<Vertex<String>> visited = new LinkedList<>();

        Vertex<String> newVertex = new Vertex<>(s);

        for (Vertex<String> vertexInList : getVertex) {
            if (vertexInList.getData().equals(s)) {
                newVertex = vertexInList;
                break;
            }
        }

        stack.add(newVertex);

        while (!stack.isEmpty()) {
            newVertex = stack.pop();

           

            if (!visited.contains(newVertex)) {
                visited.add(newVertex);
                for (Vertex<String> neighbor : newVertex.getNeighbors()) {
                    stack.addFirst(neighbor);
                }

            }

        }
        for (Vertex<String> v : visited) {
            System.out.println(v);
        }
    }

    //=======================================================================================================
    public static void main(String[] args) {

//        String anim = "|/-\\";
//        for (int x = 0; x < 100; x++) {
//            try {
//                String data = "\r" + anim.charAt(x % anim.length()) + " " + x;
//                System.out.write(data.getBytes());
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//-------------------------------------------------THIS IS OLD CODE WHEN I LOAD DATA FROM A DOCS FILE--------------------------------------------------------------------
//*****************************************************************************************************************************************************************
//        try {
//            adjacencyMatrixWithVertices = Load("Data.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(adjacencyMatrixWithVertices.length);
//        for (int i = 0; i < adjacencyMatrixWithVertices.length; i++) {
//            for (int j = 0; j < adjacencyMatrixWithVertices[i].length; j++) {
//                System.out.print(adjacencyMatrixWithVertices[i][j] + " ");
//            }
//            System.out.println("");
//        }
        //Get Name of Vertex
//        List<String> getVertexName = new ArrayList<>();
//
//        getVertex = new LinkedList<>();
//
//        for (int i = 0; i < adjacencyMatrixWithVertices.length; i++) {
//            for (int j = 0; j < adjacencyMatrixWithVertices[i].length; j++) {
//                getVertexName.add(adjacencyMatrixWithVertices[i][j]);
//            }
//            break;
//        }
//
//        for (String string : getVertexName) {
//            System.out.print(string + "");
//        }
//        adjacencyMatrix = new String[adjacencyMatrixWithVertices.length - 1][adjacencyMatrixWithVertices.length - 1];
//        for (int i = 1; i < adjacencyMatrixWithVertices.length; i++) {
//            for (int j = 0; j < adjacencyMatrixWithVertices[i].length; j++) {
//                adjacencyMatrix[i - 1][j] = adjacencyMatrixWithVertices[i][j];
//            }
//        }
        /////***************************************************************************************************************************************
        //==========================================THIS NEW CODE LOADING FROM SQL SERVER======================================================
        LoadDatabase();
        //=====================================================================================================================================    
        System.out.println("");
        System.out.println("====================ADJACENCY MATRIX=====================");

        System.out.println("Adjacency Matrix:");
        System.out.println(adjacencyMatrix.length);
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("=========================================================");

        System.out.println("");
        System.out.print("List of vertices: ");

        //Transfer data to be Vertex
        getVertex = new LinkedList<>();
        for (String string : getVertexName) {
            getVertex.add(new Vertex<>(string));
        }

        //print list of vertices
        for (Vertex<String> vertex : getVertex) {
            System.out.print(vertex.getData() + " ");
        }

        System.out.println("");
        System.out.println("");

        System.out.println("=========================================================");

        System.out.println("");
        System.out.println("List of Edges:");
        edgeList = createListOfEdge(adjacencyMatrix, getVertex);
        for (Edge<String> edge : edgeList) {
            System.out.println(edge.getSource().getData() + "" + edge.getDestination().getData() + ": " + edge.getWeight() + "  ");
        }

        System.out.println("====================INCEDENCE MATRIX======================");
        System.out.println("");
        System.out.println("Convert to Incedence Matrix:");
        System.out.println("");
        //=====Convert1()=======
        Convert1();
        //======================

        System.out.println("====================ADJACENCY LIST========================");
        System.out.println("");
        System.out.println("Convert to Adjacency List:");
        Convert2();
        System.out.println("");

        System.out.println("===========================BFS============================");

        BFS("A");
        //=============

        System.out.println("===========================DFS============================");

        //=====DFS=====
        DFS("A");
        //=============

        System.out.println("========================DIJKSTRA==========================");

        System.out.println("");

        Dijkstra<String> dijkstra = new Dijkstra<>(getVertex);
        dijkstra.Findpath("Q", "A");

        System.out.println("");

        
        System.out.println("=========================PRIM=============================");
        
        System.out.println("Minium spanning tree by Prim's Algorithm: ");
        System.out.println("");
        Prim p = new Prim(createListOfEdgeOneDirect(adjacencyMatrix, getVertex), getVertex);
        p.MST1("B");
        System.out.println("");
        
        
        System.out.println("========================KRUSKAL============================");

        System.out.println("Minium spanning tree by Kruskal Algorithm: ");
        new Kruskal<>(edgeList).MST2();

        System.out.println("");
        
        
        System.out.println("========================EULER=============================");
        
        Euler el = new Euler(createListOfEdgeOneDirect(adjacencyMatrix, getVertex), getVertex);
        el.EulerCycle("A");
        System.out.println("==========================================================");


        
    }
}
