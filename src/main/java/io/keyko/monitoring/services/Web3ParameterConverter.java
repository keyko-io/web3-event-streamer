package io.keyko.monitoring.services;

import io.keyko.monitoring.schemas.NumberParameter;
import io.keyko.monitoring.schemas.StringParameter;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Web3ParameterConverter {


  public static Object convertWeb3Type(Type parameter, String name) {

    String parameterType = parameter.getTypeAsString();

    if (parameterType.equals("address"))
      return new StringParameter(name, parameterType, Keys.toChecksumAddress(parameter.toString()));

    if (parameterType.equals("string"))
      return new StringParameter(name, parameterType, trim((String) parameter.getValue()));

    if (parameterType.startsWith("uint")||parameterType.startsWith("int")) {
      BigInteger value = (BigInteger) parameter.getValue();
      return new NumberParameter(name, parameterType, value.toString(), truncateToLong(value));
    }

    if (parameterType.startsWith("bytes"))
      return new StringParameter(name, parameterType, trim(Numeric.toHexString((byte[]) parameter.getValue())));

    return null;

  }


  private static String trim(String toTrim) {
    return toTrim
      .trim()
      .replace("\\u0000", "");
  }

    /** Given a BigInteger the function truncate to a long returning the higher order numbers
     * @param source biginteger number to truncate
     * @return long truncated
     */
  public static long truncateToLong(BigInteger source)    {
    try {
      return source.longValueExact();
    } catch (ArithmeticException ex)    {
      // BigInteger doesn't fit in a long
    }

    try {
      return Long.valueOf(
        source.toString().substring(0,  String.valueOf(Long.MAX_VALUE).length())).longValue();
    } catch (NumberFormatException ex)    {
      // BigInteger doesn't fit in a long
      return Long.valueOf(
        source.toString().substring(0,  String.valueOf(Long.MAX_VALUE).length() -1)).longValue();
    }
  }


}
