/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package net.consensys.eventeum;

@org.apache.avro.specific.AvroGenerated
public enum ContractEventStatus implements org.apache.avro.generic.GenericEnumSymbol<ContractEventStatus> {
  UNCONFIRMED, CONFIRMED, INVALIDATED;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"ContractEventStatus\",\"namespace\":\"net.consensys.eventeum\",\"symbols\":[\"UNCONFIRMED\",\"CONFIRMED\",\"INVALIDATED\"]}");

  public static org.apache.avro.Schema getClassSchema() {
    return SCHEMA$;
  }

  public org.apache.avro.Schema getSchema() {
    return SCHEMA$;
  }
}
