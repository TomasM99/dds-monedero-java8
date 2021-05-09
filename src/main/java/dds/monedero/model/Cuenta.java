package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<Movimiento>();

  public Cuenta() {
    this.saldo = 0;
  }

  public Cuenta(double montoInicial) {
    this.saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {

    this.validarMontoNoNegativo(cuanto);
    this.validarMaximaCantidadDepositos();

    this.agregarMovimiento(LocalDate.now(), cuanto, true);
    this.modificarSaldo(cuanto);
  }

  public void sacar(double cuanto) {

    this.validarMontoNoNegativo(cuanto);
    this.validarNoSacarMasDeLoQueHay(cuanto);
    this.validarNoExtraerMasDelLimite(cuanto);

    this.agregarMovimiento(LocalDate.now(), cuanto, false);
    this.modificarSaldo(-cuanto);
  }

  public void modificarSaldo(double monto) {
    this.saldo += monto;
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return this.movimientos.stream()
        .filter(movimiento -> movimiento.fueExtraido(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public void validarMontoNoNegativo(double monto) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void validarMaximaCantidadDepositos() {
    if (this.cantidadDepositos() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public void validarNoSacarMasDeLoQueHay(double monto) {
    if (this.saldo - monto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  public void validarNoExtraerMasDelLimite(double monto) {
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (monto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }

  public long cantidadDepositos() {
    return this.movimientos.stream().filter(movimiento -> movimiento.isDeposito()).count();
  }


}
