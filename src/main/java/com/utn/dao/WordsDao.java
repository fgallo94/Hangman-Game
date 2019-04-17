package com.utn.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class WordsDao {

    private ConnectionInstance conn = ConnectionInstance.getConnection();

    /**
     * Get all the words of the data base as ArrayList of String.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getAll() {
        ArrayList<String> words = new ArrayList<>();
        String sq = "select * from Word ";
        try {
            conn.connect();
            PreparedStatement st = conn.getConn().prepareStatement(sq);
            ResultSet rs = st.executeQuery();
            if (rs == null) {
                System.out.println(" No registry on database");
            }
            while (Objects.requireNonNull(rs).next()) {
                words.add(rs.getString("word"));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                conn.disconnect();
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        return words;
    }

}
