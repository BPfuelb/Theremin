public class Note implements Cloneable {

  private int posX;
  private int posY;
  private int key;
  private Double freqencey;
  private String description;
  private boolean isHalftone;

  private Steam steam;
  private Period period;
  private int periodCounter;
  private ExtendLine extendLine;

  public Note(double freqencey, String description) {

    setPosX(700 - 100);
    setPosY(0);

    setFrequence(freqencey);
    setDescription(description);

    setKey(freqencey);
    setHalftone(key);
    setSteam();
    setPeriod();
    periodCounter = 0;
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
    posX--;
  }

  public void increasePosYVal(int val) {
    posX -= val;
  }

  public void setPosX(int posX) {
    if (posX < 0 || posX > 1000)
      throw new IllegalArgumentException("posX out of range");
    this.posX = posX;
  }

  public void setPosY(int posY) {
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

  public void setSteam() {
    int key = getKey();

    if (key >= 40) {
      if (key < 50)
        steam = Steam.upSteam;
      else
        steam = Steam.downSteam;
    } else {
      if (key > 30)
        steam = Steam.downSteam;
      else
        steam = Steam.upSteam;
    }
  }

  private void setPeriod() {
    period = Period.sixteenthNote;
  }
  
  public void setPeriod(Period period) {
    this.period = period;
  }

  private void setLine() {

    if (key >= 64) {
      extendLine = ExtendLine.twoLineAbove;
    } else if (key >= 61) {
      extendLine = ExtendLine.oneLineAbove;
    } else if (key >= 40 && key <= 41) {
      extendLine = ExtendLine.oneLineBelow;
    } else if (key <= 17) {
      extendLine = ExtendLine.twoLineBelow;
    } else if (key <= 20) {
      extendLine = ExtendLine.oneLineBelow;
    } else
      extendLine = ExtendLine.noLine;

  }

  @Override
  public String toString() {
    String note = "";
    note += "posX/Y:(" + posX + " , " + posY + ")\n";
    note += "key: " + key + "\n";
    note += "freqency: " + freqencey + "\n";
    note += "description: " + description + "\n";
    note += "isHalfTone: " + (isHalftone ? "true" : "false") + "\n";
    note += "steam: " + steam.name() + "\n";
    note += "period: " + period.name() + "\n";
    note += "Line: " + extendLine.name() + "\n";
    return note;
  }

  public Note clone() {
    try {
      return (Note) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  public void increase() {
    periodCounter++;
    switch (periodCounter) {
    case 1:
      period = Period.eighthNote;
      break;
    case 3:
      period = Period.quarterNote;
      break;
    case 7:
      period = Period.halfNote;
      steam = Steam.noSteam;
      break;
    case 15:
      period = Period.wholeNote;
      steam = Steam.noSteam;
      break;
    default:
      break;
    }

  }
}
