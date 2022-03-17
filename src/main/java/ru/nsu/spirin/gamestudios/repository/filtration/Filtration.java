package ru.nsu.spirin.gamestudios.repository.filtration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Filtration {
    public enum FiltrationType {
        String,
        DoubleDate,
        SingleDate,
        Boolean,
        Integer
    }

    private final Map<String, FiltrationType> types;
    private final Map<String, String> values;

    public Filtration() {
        this.types = new HashMap<>();
        this.values = new HashMap<>();
    }

    public void addFilter(String fieldName, FiltrationType type, String value) {
        this.types.put(fieldName, type);
        this.values.put(fieldName, value);
    }

    public String buildQuery() {
        StringBuilder sb = new StringBuilder();
        boolean anyFiltersAlready = false;
        for (var key : types.keySet()) {
            if (anyFiltersAlready) {
                sb.append(" AND ");
            }
            switch (types.get(key)) {
                case String -> sb.append(queryString(key, this.values.get(key)));
                case DoubleDate -> sb.append(queryDoubleDate(key, this.values.get(key)));
                case SingleDate -> {

                }
                case Boolean -> sb.append(queryBoolean(key, this.values.get(key)));
                case Integer -> sb.append(queryInteger(key, this.values.get(key)));
            }
            anyFiltersAlready = true;
        }
        return sb.toString();
    }

    public String buildPath() {
        StringBuilder filtersString = new StringBuilder();
        boolean anyFilters = false;
        for (var key : values.keySet()) {
            String value = values.get(key);
            if (!value.isEmpty()) {
                if (anyFilters) filtersString.append("&").append(key).append("=").append(value);
                else filtersString.append(key).append("=").append(value);
                anyFilters = true;
            }
        }
        return filtersString.toString();
    }

    private String queryString(String fieldName, String value) {
        return value == null ? " TRUE " : " " + fieldName + " ILIKE '%" + value + "%' ";
    }

    private String queryDoubleDate(String fieldName, String date) {
        String leftDate = null, rightDate = null;
        if (date != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String[] dates = date.split("_-_");
                Date leftDate1 = format.parse(dates[0]);
                Date rightDate1 = format.parse(dates[1]);
                leftDate = format.format(leftDate1);
                rightDate = format.format(rightDate1);
            }
            catch (Exception ignored) {
                leftDate = null;
            }
        }
        return leftDate == null ? " TRUE " : " " + fieldName + " BETWEEN '" + leftDate + " 00:00:00' AND '" + rightDate + " 23:59:59' ";
    }

    private String queryBoolean(String fieldName, String value) {
        int intValue = 0;
        try {
            intValue = Integer.parseInt(value);
            if (intValue < 0 || intValue > 2) intValue = 0;
        }
        catch (Exception ignored) {}
        return intValue == 0 ? " TRUE " : intValue == 1 ? " " + fieldName+ " = TRUE " : " " + fieldName + " = FALSE ";
    }

    private String queryInteger(String fieldName, String value) {
        int intValue = -1;
        try {
            intValue = Integer.parseInt(value);
        }
        catch (Exception ignored) {}
        return intValue == -1 ? " TRUE " : " " + fieldName + " = " + intValue;
    }
}
