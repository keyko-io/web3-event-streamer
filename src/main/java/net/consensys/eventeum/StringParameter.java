/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package net.consensys.eventeum;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@org.apache.avro.specific.AvroGenerated
public class StringParameter extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7626022158636516734L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"StringParameter\",\"namespace\":\"net.consensys.eventeum\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");

  public static org.apache.avro.Schema getClassSchema() {
    return SCHEMA$;
  }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<StringParameter> ENCODER =
    new BinaryMessageEncoder<StringParameter>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<StringParameter> DECODER =
    new BinaryMessageDecoder<StringParameter>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<StringParameter> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<StringParameter> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<StringParameter> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<StringParameter>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this StringParameter to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a StringParameter from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a StringParameter instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static StringParameter fromByteBuffer(
    java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated
  public String name;
  @Deprecated
  public String type;
  @Deprecated
  public String value;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public StringParameter() {
  }

  /**
   * All-args constructor.
   * @param name The new value for name
   * @param type The new value for type
   * @param value The new value for value
   */
  public StringParameter(String name, String type, String value) {
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() {
    return MODEL$;
  }

  public org.apache.avro.Schema getSchema() {
    return SCHEMA$;
  }

  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
      case 0:
        return name;
      case 1:
        return type;
      case 2:
        return value;
      default:
        throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value = "unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
      case 0:
        name = (String) value$;
        break;
      case 1:
        type = (String) value$;
        break;
      case 2:
        value = (String) value$;
        break;
      default:
        throw new org.apache.avro.AvroRuntimeException("Bad index");
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
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public String getType() {
    return type;
  }


  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(String value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'value' field.
   * @return The value of the 'value' field.
   */
  public String getValue() {
    return value;
  }


  /**
   * Sets the value of the 'value' field.
   * @param value the value to set.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Creates a new StringParameter RecordBuilder.
   * @return A new StringParameter RecordBuilder
   */
  public static net.consensys.eventeum.StringParameter.Builder newBuilder() {
    return new net.consensys.eventeum.StringParameter.Builder();
  }

  /**
   * Creates a new StringParameter RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new StringParameter RecordBuilder
   */
  public static net.consensys.eventeum.StringParameter.Builder newBuilder(net.consensys.eventeum.StringParameter.Builder other) {
    if (other == null) {
      return new net.consensys.eventeum.StringParameter.Builder();
    } else {
      return new net.consensys.eventeum.StringParameter.Builder(other);
    }
  }

  /**
   * Creates a new StringParameter RecordBuilder by copying an existing StringParameter instance.
   * @param other The existing instance to copy.
   * @return A new StringParameter RecordBuilder
   */
  public static net.consensys.eventeum.StringParameter.Builder newBuilder(net.consensys.eventeum.StringParameter other) {
    if (other == null) {
      return new net.consensys.eventeum.StringParameter.Builder();
    } else {
      return new net.consensys.eventeum.StringParameter.Builder(other);
    }
  }

  /**
   * RecordBuilder for StringParameter instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<StringParameter>
    implements org.apache.avro.data.RecordBuilder<StringParameter> {

    private String name;
    private String type;
    private String value;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(net.consensys.eventeum.StringParameter.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.type)) {
        this.type = data().deepCopy(fields()[1].schema(), other.type);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.value)) {
        this.value = data().deepCopy(fields()[2].schema(), other.value);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing StringParameter instance
     * @param other The existing instance to copy.
     */
    private Builder(net.consensys.eventeum.StringParameter other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.type)) {
        this.type = data().deepCopy(fields()[1].schema(), other.type);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.value)) {
        this.value = data().deepCopy(fields()[2].schema(), other.value);
        fieldSetFlags()[2] = true;
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
    public net.consensys.eventeum.StringParameter.Builder setName(String value) {
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
    public net.consensys.eventeum.StringParameter.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
     * Gets the value of the 'type' field.
     * @return The value.
     */
    public String getType() {
      return type;
    }


    /**
     * Sets the value of the 'type' field.
     * @param value The value of 'type'.
     * @return This builder.
     */
    public net.consensys.eventeum.StringParameter.Builder setType(String value) {
      validate(fields()[1], value);
      this.type = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
     * Checks whether the 'type' field has been set.
     * @return True if the 'type' field has been set, false otherwise.
     */
    public boolean hasType() {
      return fieldSetFlags()[1];
    }


    /**
     * Clears the value of the 'type' field.
     * @return This builder.
     */
    public net.consensys.eventeum.StringParameter.Builder clearType() {
      type = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
     * Gets the value of the 'value' field.
     * @return The value.
     */
    public String getValue() {
      return value;
    }


    /**
     * Sets the value of the 'value' field.
     * @param value The value of 'value'.
     * @return This builder.
     */
    public net.consensys.eventeum.StringParameter.Builder setValue(String value) {
      validate(fields()[2], value);
      this.value = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
     * Checks whether the 'value' field has been set.
     * @return True if the 'value' field has been set, false otherwise.
     */
    public boolean hasValue() {
      return fieldSetFlags()[2];
    }


    /**
     * Clears the value of the 'value' field.
     * @return This builder.
     */
    public net.consensys.eventeum.StringParameter.Builder clearValue() {
      value = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public StringParameter build() {
      try {
        StringParameter record = new StringParameter();
        record.name = fieldSetFlags()[0] ? this.name : (String) defaultValue(fields()[0]);
        record.type = fieldSetFlags()[1] ? this.type : (String) defaultValue(fields()[1]);
        record.value = fieldSetFlags()[2] ? this.value : (String) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<StringParameter>
    WRITER$ = (org.apache.avro.io.DatumWriter<StringParameter>) MODEL$.createDatumWriter(SCHEMA$);

  @Override
  public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<StringParameter>
    READER$ = (org.apache.avro.io.DatumReader<StringParameter>) MODEL$.createDatumReader(SCHEMA$);

  @Override
  public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override
  protected boolean hasCustomCoders() {
    return true;
  }

  @Override
  public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException {
    out.writeString(this.name);

    out.writeString(this.type);

    out.writeString(this.value);

  }

  @Override
  public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.name = in.readString();

      this.type = in.readString();

      this.value = in.readString();

    } else {
      for (int i = 0; i < 3; i++) {
        switch (fieldOrder[i].pos()) {
          case 0:
            this.name = in.readString();
            break;

          case 1:
            this.type = in.readString();
            break;

          case 2:
            this.value = in.readString();
            break;

          default:
            throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










