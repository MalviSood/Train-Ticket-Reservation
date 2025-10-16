import javax.swing.*;
import java.awt.*;
import java.util.*;

class Ticket {
    int id; String name, train, seat;
    Ticket(int id, String n, String t, String s) { this.id=id; name=n; train=t; seat=s; }
}

public class TrainTicketSwing extends JFrame {
    private JTextField nameF, trainF, seatF, idF, newSeatF;
    private JTextArea area;
    private java.util.List<Ticket> tickets = new ArrayList<>();
    private int nextId=1;


    public TrainTicketSwing() {
        setTitle("Train Ticket Reservation");
        setSize(550,400); setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Insert panel
        JPanel ins = new JPanel(new GridLayout(4,2));
        ins.setBorder(BorderFactory.createTitledBorder("Insert Ticket"));
        ins.add(new JLabel("Name:")); nameF=new JTextField(); ins.add(nameF);
        ins.add(new JLabel("Train No:")); trainF=new JTextField(); ins.add(trainF);
        ins.add(new JLabel("Seat:")); seatF=new JTextField(); ins.add(seatF);
        JButton insBtn=new JButton("Insert"); ins.add(insBtn);

        // Update panel
        JPanel upd=new JPanel(new GridLayout(3,2));
        upd.setBorder(BorderFactory.createTitledBorder("Update Seat"));
        upd.add(new JLabel("Ticket ID:")); idF=new JTextField(); upd.add(idF);
        upd.add(new JLabel("New Seat:")); newSeatF=new JTextField(); upd.add(newSeatF);
        JButton updBtn=new JButton("Update"); upd.add(updBtn);

        // Display area
        area=new JTextArea(); area.setEditable(false);
        JScrollPane sp=new JScrollPane(area);
        JButton viewBtn=new JButton("View All Tickets");

        JPanel top=new JPanel(new GridLayout(1,2)); top.add(ins); top.add(upd);
        add(top,BorderLayout.NORTH); add(sp,BorderLayout.CENTER); add(viewBtn,BorderLayout.SOUTH);

        // Actions
        insBtn.addActionListener(e->insert());
        updBtn.addActionListener(e->update());
        viewBtn.addActionListener(e->view());
    }

    private void insert() {
        if(nameF.getText().isEmpty()||trainF.getText().isEmpty()||seatF.getText().isEmpty()){
            msg("All fields required!"); return; }
        tickets.add(new Ticket(nextId++,nameF.getText(),trainF.getText(),seatF.getText()));
        msg("Inserted!"); nameF.setText(""); trainF.setText(""); seatF.setText("");
    }

    private void update() {
        try{int id=Integer.parseInt(idF.getText()); String s=newSeatF.getText();
            for(Ticket t:tickets) if(t.id==id){t.seat=s; msg("Updated!"); return;}
            msg("ID not found!");
        }catch(Exception ex){msg("Invalid ID!");}
    }

    private void view() {
        StringBuilder sb=new StringBuilder("ID  Name            Train   Seat\n");
        for(Ticket t:tickets) sb.append(String.format("%-3d %-15s %-7s %-5s%n",t.id,t.name,t.train,t.seat));
        area.setText(sb.toString());
    }

    private void msg(String s){JOptionPane.showMessageDialog(this,s);}

    public static void main(String[] a){SwingUtilities.invokeLater(()->new TrainTicketSwing().setVisible(true));}


}
