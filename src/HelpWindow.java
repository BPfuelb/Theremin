import javax.swing.JOptionPane;

public class HelpWindow extends JOptionPane {

  private static final long serialVersionUID = -7257135450358148960L;
  private String help = "";

  public HelpWindow() {
    setBounds(0, 0, 600, 340);
    // show();
    this.setVisible(true);

    help += "'1-5' select especially the recorder\n";
    help += "'q' toggle if the frequency should quantified\n";
    help += " 'h' toggle if only full tone are displayed/played\n";
    help += " '+' only usable if metronome is active. Increase the beat\n";
    help += " '-' only usable if metronome is active. Decrease the beat\n";
    help += " 'm' start/mute/stop the metronome\n";
    help += " 'w' select the next waveform \n";
    help += " 'i' toggle between input method (mouse/hands)\n";
    help += " 'r' start/stop the record on the selected looper\n";
    help += " 'p' start/stop the loop on the selected looper\n";
    help += " 'd' delete the loop on the selected looper\n";

    showMessageDialog(this, help, "help", 1);
  }

}
