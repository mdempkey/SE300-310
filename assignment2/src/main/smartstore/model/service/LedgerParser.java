package edu.se300.smartstore.service;

import edu.se300.smartstore.model.LedgerEntry;
import edu.se300.smartstore.model.LedgerEntry.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LedgerParser {

    // Expected format per line: "YYYY-MM-DDTHH:MM:SS;TYPE;SKU;QTY"
    public List<LedgerEntry> parse(InputStream in) throws IOException {
        List<LedgerEntry> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(";");
                if (parts.length != 4) throw new IOException("Invalid format at line " + lineNo);
                LocalDateTime ts = LocalDateTime.parse(parts[0]);
                Type type = Type.valueOf(parts[1].toUpperCase());
                String sku = parts[2];
                int qty = Integer.parseInt(parts[3]);
                entries.add(new LedgerEntry(ts, type, sku, qty));
            }
        }
        return entries;
    }
}
