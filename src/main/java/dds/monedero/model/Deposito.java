package dds.monedero.model;

public class Deposito implements TipoDeposito {

  public boolean isDeposito() {
    return true;
  }

  public boolean isExtraccion() {
    return false;
  }
}
