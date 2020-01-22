/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package net.consensys.eventeum;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@org.apache.avro.specific.AvroGenerated
public class ContractEventDetails extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3223974792905382143L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ContractEventDetails\",\"namespace\":\"net.consensys.eventeum\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"filterId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"nodeName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"indexedParameters\",\"type\":{\"type\":\"array\",\"items\":[{\"type\":\"record\",\"name\":\"StringParameter\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]},{\"type\":\"record\",\"name\":\"NumberParameter\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"java-class\":\"java.math.BigInteger\"}]}]}},{\"name\":\"nonIndexedParameters\",\"type\":{\"type\":\"array\",\"items\":[\"StringParameter\",\"NumberParameter\"]}},{\"name\":\"transactionHash\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"logIndex\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"java-class\":\"java.math.BigInteger\"},{\"name\":\"blockNumber\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"java-class\":\"java.math.BigInteger\"},{\"name\":\"blockHash\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"address\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"status\",\"type\":{\"type\":\"enum\",\"name\":\"ContractEventStatus\",\"symbols\":[\"UNCONFIRMED\",\"CONFIRMED\",\"INVALIDATED\"]}},{\"name\":\"eventSpecificationSignature\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"networkName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<ContractEventDetails> ENCODER =
      new BinaryMessageEncoder<ContractEventDetails>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<ContractEventDetails> DECODER =
      new BinaryMessageDecoder<ContractEventDetails>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<ContractEventDetails> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<ContractEventDetails> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<ContractEventDetails> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<ContractEventDetails>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this ContractEventDetails to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a ContractEventDetails from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a ContractEventDetails instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static ContractEventDetails fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public String name;
  @Deprecated public String filterId;
  @Deprecated public String nodeName;
  @Deprecated public java.util.List<Object> indexedParameters;
  @Deprecated public java.util.List<Object> nonIndexedParameters;
  @Deprecated public String transactionHash;
  @Deprecated public String logIndex;
  @Deprecated public String blockNumber;
  @Deprecated public String blockHash;
  @Deprecated public String address;
  @Deprecated public net.consensys.eventeum.ContractEventStatus status;
  @Deprecated public String eventSpecificationSignature;
  @Deprecated public String networkName;
  @Deprecated public String id;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public ContractEventDetails() {}

  /**
   * All-args constructor.
   * @param name The new value for name
   * @param filterId The new value for filterId
   * @param nodeName The new value for nodeName
   * @param indexedParameters The new value for indexedParameters
   * @param nonIndexedParameters The new value for nonIndexedParameters
   * @param transactionHash The new value for transactionHash
   * @param logIndex The new value for logIndex
   * @param blockNumber The new value for blockNumber
   * @param blockHash The new value for blockHash
   * @param address The new value for address
   * @param status The new value for status
   * @param eventSpecificationSignature The new value for eventSpecificationSignature
   * @param networkName The new value for networkName
   * @param id The new value for id
   */
  public ContractEventDetails(String name, String filterId, String nodeName, java.util.List<Object> indexedParameters, java.util.List<Object> nonIndexedParameters, String transactionHash, String logIndex, String blockNumber, String blockHash, String address, net.consensys.eventeum.ContractEventStatus status, String eventSpecificationSignature, String networkName, String id) {
    this.name = name;
    this.filterId = filterId;
    this.nodeName = nodeName;
    this.indexedParameters = indexedParameters;
    this.nonIndexedParameters = nonIndexedParameters;
    this.transactionHash = transactionHash;
    this.logIndex = logIndex;
    this.blockNumber = blockNumber;
    this.blockHash = blockHash;
    this.address = address;
    this.status = status;
    this.eventSpecificationSignature = eventSpecificationSignature;
    this.networkName = networkName;
    this.id = id;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return name;
    case 1: return filterId;
    case 2: return nodeName;
    case 3: return indexedParameters;
    case 4: return nonIndexedParameters;
    case 5: return transactionHash;
    case 6: return logIndex;
    case 7: return blockNumber;
    case 8: return blockHash;
    case 9: return address;
    case 10: return status;
    case 11: return eventSpecificationSignature;
    case 12: return networkName;
    case 13: return id;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: name = (String)value$; break;
    case 1: filterId = (String)value$; break;
    case 2: nodeName = (String)value$; break;
    case 3: indexedParameters = (java.util.List<Object>)value$; break;
    case 4: nonIndexedParameters = (java.util.List<Object>)value$; break;
    case 5: transactionHash = (String)value$; break;
    case 6: logIndex = (String)value$; break;
    case 7: blockNumber = (String)value$; break;
    case 8: blockHash = (String)value$; break;
    case 9: address = (String)value$; break;
    case 10: status = (net.consensys.eventeum.ContractEventStatus)value$; break;
    case 11: eventSpecificationSignature = (String)value$; break;
    case 12: networkName = (String)value$; break;
    case 13: id = (String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public String getName() {
    return name;
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'filterId' field.
   * @return The value of the 'filterId' field.
   */
  public String getFilterId() {
    return filterId;
  }


  /**
   * Sets the value of the 'filterId' field.
   * @param value the value to set.
   */
  public void setFilterId(String value) {
    this.filterId = value;
  }

  /**
   * Gets the value of the 'nodeName' field.
   * @return The value of the 'nodeName' field.
   */
  public String getNodeName() {
    return nodeName;
  }


  /**
   * Sets the value of the 'nodeName' field.
   * @param value the value to set.
   */
  public void setNodeName(String value) {
    this.nodeName = value;
  }

  /**
   * Gets the value of the 'indexedParameters' field.
   * @return The value of the 'indexedParameters' field.
   */
  public java.util.List<Object> getIndexedParameters() {
    return indexedParameters;
  }


  /**
   * Sets the value of the 'indexedParameters' field.
   * @param value the value to set.
   */
  public void setIndexedParameters(java.util.List<Object> value) {
    this.indexedParameters = value;
  }

  /**
   * Gets the value of the 'nonIndexedParameters' field.
   * @return The value of the 'nonIndexedParameters' field.
   */
  public java.util.List<Object> getNonIndexedParameters() {
    return nonIndexedParameters;
  }


  /**
   * Sets the value of the 'nonIndexedParameters' field.
   * @param value the value to set.
   */
  public void setNonIndexedParameters(java.util.List<Object> value) {
    this.nonIndexedParameters = value;
  }

  /**
   * Gets the value of the 'transactionHash' field.
   * @return The value of the 'transactionHash' field.
   */
  public String getTransactionHash() {
    return transactionHash;
  }


  /**
   * Sets the value of the 'transactionHash' field.
   * @param value the value to set.
   */
  public void setTransactionHash(String value) {
    this.transactionHash = value;
  }

  /**
   * Gets the value of the 'logIndex' field.
   * @return The value of the 'logIndex' field.
   */
  public String getLogIndex() {
    return logIndex;
  }


  /**
   * Sets the value of the 'logIndex' field.
   * @param value the value to set.
   */
  public void setLogIndex(String value) {
    this.logIndex = value;
  }

  /**
   * Gets the value of the 'blockNumber' field.
   * @return The value of the 'blockNumber' field.
   */
  public String getBlockNumber() {
    return blockNumber;
  }


  /**
   * Sets the value of the 'blockNumber' field.
   * @param value the value to set.
   */
  public void setBlockNumber(String value) {
    this.blockNumber = value;
  }

  /**
   * Gets the value of the 'blockHash' field.
   * @return The value of the 'blockHash' field.
   */
  public String getBlockHash() {
    return blockHash;
  }


  /**
   * Sets the value of the 'blockHash' field.
   * @param value the value to set.
   */
  public void setBlockHash(String value) {
    this.blockHash = value;
  }

  /**
   * Gets the value of the 'address' field.
   * @return The value of the 'address' field.
   */
  public String getAddress() {
    return address;
  }


  /**
   * Sets the value of the 'address' field.
   * @param value the value to set.
   */
  public void setAddress(String value) {
    this.address = value;
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public net.consensys.eventeum.ContractEventStatus getStatus() {
    return status;
  }


  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(net.consensys.eventeum.ContractEventStatus value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'eventSpecificationSignature' field.
   * @return The value of the 'eventSpecificationSignature' field.
   */
  public String getEventSpecificationSignature() {
    return eventSpecificationSignature;
  }


  /**
   * Sets the value of the 'eventSpecificationSignature' field.
   * @param value the value to set.
   */
  public void setEventSpecificationSignature(String value) {
    this.eventSpecificationSignature = value;
  }

  /**
   * Gets the value of the 'networkName' field.
   * @return The value of the 'networkName' field.
   */
  public String getNetworkName() {
    return networkName;
  }


  /**
   * Sets the value of the 'networkName' field.
   * @param value the value to set.
   */
  public void setNetworkName(String value) {
    this.networkName = value;
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public String getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(String value) {
    this.id = value;
  }

  /**
   * Creates a new ContractEventDetails RecordBuilder.
   * @return A new ContractEventDetails RecordBuilder
   */
  public static net.consensys.eventeum.ContractEventDetails.Builder newBuilder() {
    return new net.consensys.eventeum.ContractEventDetails.Builder();
  }

  /**
   * Creates a new ContractEventDetails RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new ContractEventDetails RecordBuilder
   */
  public static net.consensys.eventeum.ContractEventDetails.Builder newBuilder(net.consensys.eventeum.ContractEventDetails.Builder other) {
    if (other == null) {
      return new net.consensys.eventeum.ContractEventDetails.Builder();
    } else {
      return new net.consensys.eventeum.ContractEventDetails.Builder(other);
    }
  }

  /**
   * Creates a new ContractEventDetails RecordBuilder by copying an existing ContractEventDetails instance.
   * @param other The existing instance to copy.
   * @return A new ContractEventDetails RecordBuilder
   */
  public static net.consensys.eventeum.ContractEventDetails.Builder newBuilder(net.consensys.eventeum.ContractEventDetails other) {
    if (other == null) {
      return new net.consensys.eventeum.ContractEventDetails.Builder();
    } else {
      return new net.consensys.eventeum.ContractEventDetails.Builder(other);
    }
  }

  /**
   * RecordBuilder for ContractEventDetails instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ContractEventDetails>
    implements org.apache.avro.data.RecordBuilder<ContractEventDetails> {

    private String name;
    private String filterId;
    private String nodeName;
    private java.util.List<Object> indexedParameters;
    private java.util.List<Object> nonIndexedParameters;
    private String transactionHash;
    private String logIndex;
    private String blockNumber;
    private String blockHash;
    private String address;
    private net.consensys.eventeum.ContractEventStatus status;
    private String eventSpecificationSignature;
    private String networkName;
    private String id;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(net.consensys.eventeum.ContractEventDetails.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.filterId)) {
        this.filterId = data().deepCopy(fields()[1].schema(), other.filterId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.nodeName)) {
        this.nodeName = data().deepCopy(fields()[2].schema(), other.nodeName);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.indexedParameters)) {
        this.indexedParameters = data().deepCopy(fields()[3].schema(), other.indexedParameters);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.nonIndexedParameters)) {
        this.nonIndexedParameters = data().deepCopy(fields()[4].schema(), other.nonIndexedParameters);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.transactionHash)) {
        this.transactionHash = data().deepCopy(fields()[5].schema(), other.transactionHash);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.logIndex)) {
        this.logIndex = data().deepCopy(fields()[6].schema(), other.logIndex);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.blockNumber)) {
        this.blockNumber = data().deepCopy(fields()[7].schema(), other.blockNumber);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (isValidValue(fields()[8], other.blockHash)) {
        this.blockHash = data().deepCopy(fields()[8].schema(), other.blockHash);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
      if (isValidValue(fields()[9], other.address)) {
        this.address = data().deepCopy(fields()[9].schema(), other.address);
        fieldSetFlags()[9] = other.fieldSetFlags()[9];
      }
      if (isValidValue(fields()[10], other.status)) {
        this.status = data().deepCopy(fields()[10].schema(), other.status);
        fieldSetFlags()[10] = other.fieldSetFlags()[10];
      }
      if (isValidValue(fields()[11], other.eventSpecificationSignature)) {
        this.eventSpecificationSignature = data().deepCopy(fields()[11].schema(), other.eventSpecificationSignature);
        fieldSetFlags()[11] = other.fieldSetFlags()[11];
      }
      if (isValidValue(fields()[12], other.networkName)) {
        this.networkName = data().deepCopy(fields()[12].schema(), other.networkName);
        fieldSetFlags()[12] = other.fieldSetFlags()[12];
      }
      if (isValidValue(fields()[13], other.id)) {
        this.id = data().deepCopy(fields()[13].schema(), other.id);
        fieldSetFlags()[13] = other.fieldSetFlags()[13];
      }
    }

    /**
     * Creates a Builder by copying an existing ContractEventDetails instance
     * @param other The existing instance to copy.
     */
    private Builder(net.consensys.eventeum.ContractEventDetails other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.filterId)) {
        this.filterId = data().deepCopy(fields()[1].schema(), other.filterId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.nodeName)) {
        this.nodeName = data().deepCopy(fields()[2].schema(), other.nodeName);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.indexedParameters)) {
        this.indexedParameters = data().deepCopy(fields()[3].schema(), other.indexedParameters);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.nonIndexedParameters)) {
        this.nonIndexedParameters = data().deepCopy(fields()[4].schema(), other.nonIndexedParameters);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.transactionHash)) {
        this.transactionHash = data().deepCopy(fields()[5].schema(), other.transactionHash);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.logIndex)) {
        this.logIndex = data().deepCopy(fields()[6].schema(), other.logIndex);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.blockNumber)) {
        this.blockNumber = data().deepCopy(fields()[7].schema(), other.blockNumber);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.blockHash)) {
        this.blockHash = data().deepCopy(fields()[8].schema(), other.blockHash);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.address)) {
        this.address = data().deepCopy(fields()[9].schema(), other.address);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.status)) {
        this.status = data().deepCopy(fields()[10].schema(), other.status);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.eventSpecificationSignature)) {
        this.eventSpecificationSignature = data().deepCopy(fields()[11].schema(), other.eventSpecificationSignature);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.networkName)) {
        this.networkName = data().deepCopy(fields()[12].schema(), other.networkName);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.id)) {
        this.id = data().deepCopy(fields()[13].schema(), other.id);
        fieldSetFlags()[13] = true;
      }
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public String getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setName(String value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'filterId' field.
      * @return The value.
      */
    public String getFilterId() {
      return filterId;
    }


    /**
      * Sets the value of the 'filterId' field.
      * @param value The value of 'filterId'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setFilterId(String value) {
      validate(fields()[1], value);
      this.filterId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'filterId' field has been set.
      * @return True if the 'filterId' field has been set, false otherwise.
      */
    public boolean hasFilterId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'filterId' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearFilterId() {
      filterId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'nodeName' field.
      * @return The value.
      */
    public String getNodeName() {
      return nodeName;
    }


    /**
      * Sets the value of the 'nodeName' field.
      * @param value The value of 'nodeName'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setNodeName(String value) {
      validate(fields()[2], value);
      this.nodeName = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'nodeName' field has been set.
      * @return True if the 'nodeName' field has been set, false otherwise.
      */
    public boolean hasNodeName() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'nodeName' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearNodeName() {
      nodeName = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'indexedParameters' field.
      * @return The value.
      */
    public java.util.List<Object> getIndexedParameters() {
      return indexedParameters;
    }


    /**
      * Sets the value of the 'indexedParameters' field.
      * @param value The value of 'indexedParameters'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setIndexedParameters(java.util.List<Object> value) {
      validate(fields()[3], value);
      this.indexedParameters = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'indexedParameters' field has been set.
      * @return True if the 'indexedParameters' field has been set, false otherwise.
      */
    public boolean hasIndexedParameters() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'indexedParameters' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearIndexedParameters() {
      indexedParameters = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'nonIndexedParameters' field.
      * @return The value.
      */
    public java.util.List<Object> getNonIndexedParameters() {
      return nonIndexedParameters;
    }


    /**
      * Sets the value of the 'nonIndexedParameters' field.
      * @param value The value of 'nonIndexedParameters'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setNonIndexedParameters(java.util.List<Object> value) {
      validate(fields()[4], value);
      this.nonIndexedParameters = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'nonIndexedParameters' field has been set.
      * @return True if the 'nonIndexedParameters' field has been set, false otherwise.
      */
    public boolean hasNonIndexedParameters() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'nonIndexedParameters' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearNonIndexedParameters() {
      nonIndexedParameters = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionHash' field.
      * @return The value.
      */
    public String getTransactionHash() {
      return transactionHash;
    }


    /**
      * Sets the value of the 'transactionHash' field.
      * @param value The value of 'transactionHash'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setTransactionHash(String value) {
      validate(fields()[5], value);
      this.transactionHash = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionHash' field has been set.
      * @return True if the 'transactionHash' field has been set, false otherwise.
      */
    public boolean hasTransactionHash() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'transactionHash' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearTransactionHash() {
      transactionHash = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'logIndex' field.
      * @return The value.
      */
    public String getLogIndex() {
      return logIndex;
    }


    /**
      * Sets the value of the 'logIndex' field.
      * @param value The value of 'logIndex'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setLogIndex(String value) {
      validate(fields()[6], value);
      this.logIndex = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'logIndex' field has been set.
      * @return True if the 'logIndex' field has been set, false otherwise.
      */
    public boolean hasLogIndex() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'logIndex' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearLogIndex() {
      logIndex = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'blockNumber' field.
      * @return The value.
      */
    public String getBlockNumber() {
      return blockNumber;
    }


    /**
      * Sets the value of the 'blockNumber' field.
      * @param value The value of 'blockNumber'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setBlockNumber(String value) {
      validate(fields()[7], value);
      this.blockNumber = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'blockNumber' field has been set.
      * @return True if the 'blockNumber' field has been set, false otherwise.
      */
    public boolean hasBlockNumber() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'blockNumber' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearBlockNumber() {
      blockNumber = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'blockHash' field.
      * @return The value.
      */
    public String getBlockHash() {
      return blockHash;
    }


    /**
      * Sets the value of the 'blockHash' field.
      * @param value The value of 'blockHash'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setBlockHash(String value) {
      validate(fields()[8], value);
      this.blockHash = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'blockHash' field has been set.
      * @return True if the 'blockHash' field has been set, false otherwise.
      */
    public boolean hasBlockHash() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'blockHash' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearBlockHash() {
      blockHash = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'address' field.
      * @return The value.
      */
    public String getAddress() {
      return address;
    }


    /**
      * Sets the value of the 'address' field.
      * @param value The value of 'address'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setAddress(String value) {
      validate(fields()[9], value);
      this.address = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'address' field has been set.
      * @return True if the 'address' field has been set, false otherwise.
      */
    public boolean hasAddress() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'address' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearAddress() {
      address = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public net.consensys.eventeum.ContractEventStatus getStatus() {
      return status;
    }


    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setStatus(net.consensys.eventeum.ContractEventStatus value) {
      validate(fields()[10], value);
      this.status = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearStatus() {
      status = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /**
      * Gets the value of the 'eventSpecificationSignature' field.
      * @return The value.
      */
    public String getEventSpecificationSignature() {
      return eventSpecificationSignature;
    }


    /**
      * Sets the value of the 'eventSpecificationSignature' field.
      * @param value The value of 'eventSpecificationSignature'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setEventSpecificationSignature(String value) {
      validate(fields()[11], value);
      this.eventSpecificationSignature = value;
      fieldSetFlags()[11] = true;
      return this;
    }

    /**
      * Checks whether the 'eventSpecificationSignature' field has been set.
      * @return True if the 'eventSpecificationSignature' field has been set, false otherwise.
      */
    public boolean hasEventSpecificationSignature() {
      return fieldSetFlags()[11];
    }


    /**
      * Clears the value of the 'eventSpecificationSignature' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearEventSpecificationSignature() {
      eventSpecificationSignature = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /**
      * Gets the value of the 'networkName' field.
      * @return The value.
      */
    public String getNetworkName() {
      return networkName;
    }


    /**
      * Sets the value of the 'networkName' field.
      * @param value The value of 'networkName'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setNetworkName(String value) {
      validate(fields()[12], value);
      this.networkName = value;
      fieldSetFlags()[12] = true;
      return this;
    }

    /**
      * Checks whether the 'networkName' field has been set.
      * @return True if the 'networkName' field has been set, false otherwise.
      */
    public boolean hasNetworkName() {
      return fieldSetFlags()[12];
    }


    /**
      * Clears the value of the 'networkName' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearNetworkName() {
      networkName = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public String getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder setId(String value) {
      validate(fields()[13], value);
      this.id = value;
      fieldSetFlags()[13] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[13];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public net.consensys.eventeum.ContractEventDetails.Builder clearId() {
      id = null;
      fieldSetFlags()[13] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ContractEventDetails build() {
      try {
        ContractEventDetails record = new ContractEventDetails();
        record.name = fieldSetFlags()[0] ? this.name : (String) defaultValue(fields()[0]);
        record.filterId = fieldSetFlags()[1] ? this.filterId : (String) defaultValue(fields()[1]);
        record.nodeName = fieldSetFlags()[2] ? this.nodeName : (String) defaultValue(fields()[2]);
        record.indexedParameters = fieldSetFlags()[3] ? this.indexedParameters : (java.util.List<Object>) defaultValue(fields()[3]);
        record.nonIndexedParameters = fieldSetFlags()[4] ? this.nonIndexedParameters : (java.util.List<Object>) defaultValue(fields()[4]);
        record.transactionHash = fieldSetFlags()[5] ? this.transactionHash : (String) defaultValue(fields()[5]);
        record.logIndex = fieldSetFlags()[6] ? this.logIndex : (String) defaultValue(fields()[6]);
        record.blockNumber = fieldSetFlags()[7] ? this.blockNumber : (String) defaultValue(fields()[7]);
        record.blockHash = fieldSetFlags()[8] ? this.blockHash : (String) defaultValue(fields()[8]);
        record.address = fieldSetFlags()[9] ? this.address : (String) defaultValue(fields()[9]);
        record.status = fieldSetFlags()[10] ? this.status : (net.consensys.eventeum.ContractEventStatus) defaultValue(fields()[10]);
        record.eventSpecificationSignature = fieldSetFlags()[11] ? this.eventSpecificationSignature : (String) defaultValue(fields()[11]);
        record.networkName = fieldSetFlags()[12] ? this.networkName : (String) defaultValue(fields()[12]);
        record.id = fieldSetFlags()[13] ? this.id : (String) defaultValue(fields()[13]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<ContractEventDetails>
    WRITER$ = (org.apache.avro.io.DatumWriter<ContractEventDetails>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<ContractEventDetails>
    READER$ = (org.apache.avro.io.DatumReader<ContractEventDetails>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










