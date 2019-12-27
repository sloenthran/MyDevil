package pl.nogacz.mydevil.util.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MyDevilSocket {
    private static Logger LOGGER = LoggerFactory.getLogger(MyDevilSocket.class);
    private Process process;

    private static MyDevilSocket instance = null;

    public static MyDevilSocket getInstance() {
        if(instance == null) {
            synchronized(MyDevilSocket.class) {
                if(instance == null) {
                    try {
                        instance = new MyDevilSocket();
                    } catch(IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }

        return instance;
    }

    private MyDevilSocket() throws IOException {
        process = Runtime.getRuntime().exec("nc -U /var/run/devil2.sock");
    }

    public String getMailsDomain() {
        return sendCommand("[\"--json\", \"mail\", \"list\"]");
    }

    public String getMailboxesAndAliasesFromDomain(String domain) {
        return sendCommand("[\"--json\", \"mail\", \"list\", \""+ domain +"\"]");
    }

    public String addMail(String email, String password) {
        return sendCommand("[\"--json\", \"mail\", \"account\", \"add\", \""+ email +"\"]", password);
    }

    public String removeMail(String email) {
        return sendCommand("[\"--json\", \"mail\", \"account\", \"del\", \""+ email +"\"]");
    }

    public String getDomains() {
        return sendCommand("[\"--json\", \"www\", \"list\"]");
    }

    private String sendCommand(String command) {
        return sendCommand(command, null);
    }

    private String sendCommand(String command, String commandTwo) {
        StringBuilder output = new StringBuilder();

        try {
            String stringCache;

            if(!process.isAlive()) {
                process = Runtime.getRuntime().exec("nc -U /var/run/devil2.sock");
            }

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(command);
            writer.flush();

            if(commandTwo != null) {
                writer.write(commandTwo);
                writer.flush();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((stringCache = reader.readLine()) != null) {
                output.append(stringCache);
            }

            writer.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        if(output.toString().isEmpty()) {
            output = new StringBuilder(sendCommand(command, commandTwo));
        }
        return output.toString();
    }
}
