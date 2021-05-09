package dds.monedero.model;

public class Extraccion implements TipoDeposito {

  public boolean isDeposito() {
    return false;
  }

  public boolean isExtraccion() {
    return true;
  }
}
