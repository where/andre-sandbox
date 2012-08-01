Optional formats that implementations may validate against.

http://tools.ietf.org/html/draft-zyp-json-schema-03#section-5.23

  This property defines the type of data, content type, or microformat
  to be expected in the instance property values.  A format attribute
  MAY be one of the values listed below, and if so, SHOULD adhere to
  the semantics describing for the format.  A format SHOULD only be
  used to give meaning to primitive types (string, integer, number, or
  boolean).  Validators MAY (but are not required to) validate that the
  instance values conform to a format.

EqualExpertsJsonValidator supports these formats:
  date-time
  date
  time
  utc-millisec 
but not these:
  email
  uri
