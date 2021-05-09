package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  private double monto;
  private TipoDeposito tipo;

  public Movimiento(LocalDate fecha, double monto, TipoDeposito tipo) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipo = tipo;
  }

  public TipoDeposito getTipo() {
    return tipo;
  }

  public double getMonto() {
    return monto;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return this.tipo.isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return this.tipo.isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

}
