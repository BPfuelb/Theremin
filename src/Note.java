public class Note {

  private int posX;
  private int posY;
  private int key;
  private Double freqencey;
  private String description;
  private boolean isHalftone;

  private Steam steam;
  private Period period;
  private ExtendLine extendLine;

  public Note(double freqencey, String description) {
    setPosX(200);
    setPosY(0);

    setFrequence(freqencey);
    setDescription(description);

    setKey(freqencey);
    setHalftone(key);
    setSteam();
    setPeriod();
    setLine();
  }

  public Double getFreqencey() {
    return freqencey;
  }
  
    public String getDescription() {
    return description;
  }

  public int getKey() {
    return key;
  }

  public boolean IsHalftone() {
    return isHalftone;
  }

  public Period getPeriod() {
    return period;
  }

  public Steam getSteam() {
    return steam;
  }

  public ExtendLine getLine() {
    return extendLine;
  }

  public int getPosX() {
    return posX;
  }

  public int getPosY() {
    return posY;
  }

  public void increasePosY() {
    posY++;
  }

  private void setPosX(int posX) {
    if (posX < 0 || posX > 1000)
      throw new IllegalArgumentException("posX out of range");
    this.posX = posX;
  }

  private void setPosY(int posY) {
    if (posY < 0 || posY > 1000)
      throw new IllegalArgumentException("posY out of range");
    this.posY = posY;
  }

  private void setFrequence(double freqence) {
    if (freqence <= 0 || freqence > 16000)
      throw new IllegalArgumentException("freqence out of range");
    this.freqencey = freqence;
  }

  private void setDescription(String description) {
    if (description == null)
      throw new NullPointerException("description should not be null");
    this.description = description;
  }

  private void setKey(Double frequency) {
    if (frequency <= 0 || frequency > 16000)
      throw new IllegalArgumentException("freqence out of range");
    key = MusicalScale.getKeyToFreqency(frequency);
  }

  private void setHalftone(int key) {
    isHalftone = MusicalScale.isNoteHalfStep(key);
  }

  private void setSteam() {
    steam = Steam.noSteam;
  }

  private void setPeriod() {
    period = Period.quarterNote;
  }

  private void setLine() {
    extendLine = ExtendLine.noLine;
  }
}
