package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta(0);
  }

  @Test
  public void AlPonerDineroEnUnaCuentaSeActualizaElSaldo() {
    cuenta.poner(1500);
    assertEquals(1500,cuenta.getSaldo());
  }

  @Test
  public void AlSacarDineroEnUnaCuentaSeActualizaElSaldo() {
    cuenta.setSaldo(1000);
    cuenta.sacar(350);
    assertEquals(650,cuenta.getSaldo());
  }

  @Test
  public void NoPodemosPonerUnMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  public void PodemosDepositarTresVecesYSeActualizaElSaldo() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(3856,cuenta.getSaldo());
  }

  @Test
  public void NoPodemosDepositarMasDeTresVeces() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }

  @Test
  public void NoPodemosExtraerMasDeLoQueTenemos() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.sacar(1001);
    });
  }

  @Test
  public void NoPodemosExtaerMasDeMil() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void NoPodemosSacarUnMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

}