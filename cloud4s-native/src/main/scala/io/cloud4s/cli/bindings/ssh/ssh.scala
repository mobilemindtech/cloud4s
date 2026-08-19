package io.cloud4s.cli.bindings.ssh


import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*
import _root_.scala.scalanative.*

object predef:
    private[ssh] trait _BindgenEnumCInt[T](using eq: T =:= CInt):
      given Tag[T] = Tag.Int.asInstanceOf[Tag[T]]
      extension (inline t: T)
        inline def value: CInt = eq.apply(t)
        inline def int: CInt = eq.apply(t).toInt
    private[ssh] trait _BindgenEnumCUnsignedInt[T](using eq: T =:= CUnsignedInt):
      given Tag[T] = Tag.UInt.asInstanceOf[Tag[T]]
      extension (inline t: T)
        inline def value: CUnsignedInt = eq.apply(t)
        inline def int: CInt = eq.apply(t).toInt
        inline def uint: CUnsignedInt = eq.apply(t)

object enumerations:
  import predef.*
  opaque type ssh_auth_e = CInt
  object ssh_auth_e extends _BindgenEnumCInt[ssh_auth_e]:
    given _tag: Tag[ssh_auth_e] = Tag.Int
    inline def define(inline a: CInt): ssh_auth_e = a
    val SSH_AUTH_SUCCESS = define(0)
    val SSH_AUTH_DENIED = define(1)
    val SSH_AUTH_PARTIAL = define(2)
    val SSH_AUTH_INFO = define(3)
    val SSH_AUTH_AGAIN = define(4)
    val SSH_AUTH_ERROR = define(-1)
    inline def getName(inline value: ssh_auth_e): Option[String] =
      inline value match
        case SSH_AUTH_SUCCESS => Some("SSH_AUTH_SUCCESS")
        case SSH_AUTH_DENIED => Some("SSH_AUTH_DENIED")
        case SSH_AUTH_PARTIAL => Some("SSH_AUTH_PARTIAL")
        case SSH_AUTH_INFO => Some("SSH_AUTH_INFO")
        case SSH_AUTH_AGAIN => Some("SSH_AUTH_AGAIN")
        case SSH_AUTH_ERROR => Some("SSH_AUTH_ERROR")
        case _ => _root_.scala.None
    extension (a: ssh_auth_e)
      inline def &(b: ssh_auth_e): ssh_auth_e = a & b
      inline def |(b: ssh_auth_e): ssh_auth_e = a | b
      inline def is(b: ssh_auth_e): Boolean = (a & b) == b

  opaque type ssh_channel_requests_e = CUnsignedInt
  object ssh_channel_requests_e extends _BindgenEnumCUnsignedInt[ssh_channel_requests_e]:
    given _tag: Tag[ssh_channel_requests_e] = Tag.UInt
    inline def define(inline a: Long): ssh_channel_requests_e = a.toUInt
    val SSH_CHANNEL_REQUEST_UNKNOWN = define(0)
    val SSH_CHANNEL_REQUEST_PTY = define(1)
    val SSH_CHANNEL_REQUEST_EXEC = define(2)
    val SSH_CHANNEL_REQUEST_SHELL = define(3)
    val SSH_CHANNEL_REQUEST_ENV = define(4)
    val SSH_CHANNEL_REQUEST_SUBSYSTEM = define(5)
    val SSH_CHANNEL_REQUEST_WINDOW_CHANGE = define(6)
    val SSH_CHANNEL_REQUEST_X11 = define(7)
    inline def getName(inline value: ssh_channel_requests_e): Option[String] =
      inline value match
        case SSH_CHANNEL_REQUEST_UNKNOWN => Some("SSH_CHANNEL_REQUEST_UNKNOWN")
        case SSH_CHANNEL_REQUEST_PTY => Some("SSH_CHANNEL_REQUEST_PTY")
        case SSH_CHANNEL_REQUEST_EXEC => Some("SSH_CHANNEL_REQUEST_EXEC")
        case SSH_CHANNEL_REQUEST_SHELL => Some("SSH_CHANNEL_REQUEST_SHELL")
        case SSH_CHANNEL_REQUEST_ENV => Some("SSH_CHANNEL_REQUEST_ENV")
        case SSH_CHANNEL_REQUEST_SUBSYSTEM => Some("SSH_CHANNEL_REQUEST_SUBSYSTEM")
        case SSH_CHANNEL_REQUEST_WINDOW_CHANGE => Some("SSH_CHANNEL_REQUEST_WINDOW_CHANGE")
        case SSH_CHANNEL_REQUEST_X11 => Some("SSH_CHANNEL_REQUEST_X11")
        case _ => _root_.scala.None
    extension (a: ssh_channel_requests_e)
      inline def &(b: ssh_channel_requests_e): ssh_channel_requests_e = a & b
      inline def |(b: ssh_channel_requests_e): ssh_channel_requests_e = a | b
      inline def is(b: ssh_channel_requests_e): Boolean = (a & b) == b

  opaque type ssh_channel_type_e = CUnsignedInt
  object ssh_channel_type_e extends _BindgenEnumCUnsignedInt[ssh_channel_type_e]:
    given _tag: Tag[ssh_channel_type_e] = Tag.UInt
    inline def define(inline a: Long): ssh_channel_type_e = a.toUInt
    val SSH_CHANNEL_UNKNOWN = define(0)
    val SSH_CHANNEL_SESSION = define(1)
    val SSH_CHANNEL_DIRECT_TCPIP = define(2)
    val SSH_CHANNEL_FORWARDED_TCPIP = define(3)
    val SSH_CHANNEL_X11 = define(4)
    val SSH_CHANNEL_AUTH_AGENT = define(5)
    inline def getName(inline value: ssh_channel_type_e): Option[String] =
      inline value match
        case SSH_CHANNEL_UNKNOWN => Some("SSH_CHANNEL_UNKNOWN")
        case SSH_CHANNEL_SESSION => Some("SSH_CHANNEL_SESSION")
        case SSH_CHANNEL_DIRECT_TCPIP => Some("SSH_CHANNEL_DIRECT_TCPIP")
        case SSH_CHANNEL_FORWARDED_TCPIP => Some("SSH_CHANNEL_FORWARDED_TCPIP")
        case SSH_CHANNEL_X11 => Some("SSH_CHANNEL_X11")
        case SSH_CHANNEL_AUTH_AGENT => Some("SSH_CHANNEL_AUTH_AGENT")
        case _ => _root_.scala.None
    extension (a: ssh_channel_type_e)
      inline def &(b: ssh_channel_type_e): ssh_channel_type_e = a & b
      inline def |(b: ssh_channel_type_e): ssh_channel_type_e = a | b
      inline def is(b: ssh_channel_type_e): Boolean = (a & b) == b

  opaque type ssh_connector_flags_e = CUnsignedInt
  object ssh_connector_flags_e extends _BindgenEnumCUnsignedInt[ssh_connector_flags_e]:
    given _tag: Tag[ssh_connector_flags_e] = Tag.UInt
    inline def define(inline a: Long): ssh_connector_flags_e = a.toUInt
    val SSH_CONNECTOR_STDOUT = define(1)
    val SSH_CONNECTOR_STDINOUT = define(1)
    val SSH_CONNECTOR_STDERR = define(2)
    val SSH_CONNECTOR_BOTH = define(3)
    inline def getName(inline value: ssh_connector_flags_e): Option[String] =
      inline value match
        case SSH_CONNECTOR_STDOUT => Some("SSH_CONNECTOR_STDOUT")
        case SSH_CONNECTOR_STDINOUT => Some("SSH_CONNECTOR_STDINOUT")
        case SSH_CONNECTOR_STDERR => Some("SSH_CONNECTOR_STDERR")
        case SSH_CONNECTOR_BOTH => Some("SSH_CONNECTOR_BOTH")
        case _ => _root_.scala.None
    extension (a: ssh_connector_flags_e)
      inline def &(b: ssh_connector_flags_e): ssh_connector_flags_e = a & b
      inline def |(b: ssh_connector_flags_e): ssh_connector_flags_e = a | b
      inline def is(b: ssh_connector_flags_e): Boolean = (a & b) == b

  /**
   * @}
  */
  opaque type ssh_control_master_options_e = CUnsignedInt
  object ssh_control_master_options_e extends _BindgenEnumCUnsignedInt[ssh_control_master_options_e]:
    given _tag: Tag[ssh_control_master_options_e] = Tag.UInt
    inline def define(inline a: Long): ssh_control_master_options_e = a.toUInt
    val SSH_CONTROL_MASTER_NO = define(0)
    val SSH_CONTROL_MASTER_AUTO = define(1)
    val SSH_CONTROL_MASTER_YES = define(2)
    val SSH_CONTROL_MASTER_ASK = define(3)
    val SSH_CONTROL_MASTER_AUTOASK = define(4)
    inline def getName(inline value: ssh_control_master_options_e): Option[String] =
      inline value match
        case SSH_CONTROL_MASTER_NO => Some("SSH_CONTROL_MASTER_NO")
        case SSH_CONTROL_MASTER_AUTO => Some("SSH_CONTROL_MASTER_AUTO")
        case SSH_CONTROL_MASTER_YES => Some("SSH_CONTROL_MASTER_YES")
        case SSH_CONTROL_MASTER_ASK => Some("SSH_CONTROL_MASTER_ASK")
        case SSH_CONTROL_MASTER_AUTOASK => Some("SSH_CONTROL_MASTER_AUTOASK")
        case _ => _root_.scala.None
    extension (a: ssh_control_master_options_e)
      inline def &(b: ssh_control_master_options_e): ssh_control_master_options_e = a & b
      inline def |(b: ssh_control_master_options_e): ssh_control_master_options_e = a | b
      inline def is(b: ssh_control_master_options_e): Boolean = (a & b) == b

  opaque type ssh_error_types_e = CUnsignedInt
  object ssh_error_types_e extends _BindgenEnumCUnsignedInt[ssh_error_types_e]:
    given _tag: Tag[ssh_error_types_e] = Tag.UInt
    inline def define(inline a: Long): ssh_error_types_e = a.toUInt
    val SSH_NO_ERROR = define(0)
    val SSH_REQUEST_DENIED = define(1)
    val SSH_FATAL = define(2)
    val SSH_EINTR = define(3)
    inline def getName(inline value: ssh_error_types_e): Option[String] =
      inline value match
        case SSH_NO_ERROR => Some("SSH_NO_ERROR")
        case SSH_REQUEST_DENIED => Some("SSH_REQUEST_DENIED")
        case SSH_FATAL => Some("SSH_FATAL")
        case SSH_EINTR => Some("SSH_EINTR")
        case _ => _root_.scala.None
    extension (a: ssh_error_types_e)
      inline def &(b: ssh_error_types_e): ssh_error_types_e = a & b
      inline def |(b: ssh_error_types_e): ssh_error_types_e = a | b
      inline def is(b: ssh_error_types_e): Boolean = (a & b) == b

  /**
   * @}
  */
  opaque type ssh_file_format_e = CUnsignedInt
  object ssh_file_format_e extends _BindgenEnumCUnsignedInt[ssh_file_format_e]:
    given _tag: Tag[ssh_file_format_e] = Tag.UInt
    inline def define(inline a: Long): ssh_file_format_e = a.toUInt
    val SSH_FILE_FORMAT_DEFAULT = define(0)
    val SSH_FILE_FORMAT_OPENSSH = define(1)
    val SSH_FILE_FORMAT_PEM = define(2)
    inline def getName(inline value: ssh_file_format_e): Option[String] =
      inline value match
        case SSH_FILE_FORMAT_DEFAULT => Some("SSH_FILE_FORMAT_DEFAULT")
        case SSH_FILE_FORMAT_OPENSSH => Some("SSH_FILE_FORMAT_OPENSSH")
        case SSH_FILE_FORMAT_PEM => Some("SSH_FILE_FORMAT_PEM")
        case _ => _root_.scala.None
    extension (a: ssh_file_format_e)
      inline def &(b: ssh_file_format_e): ssh_file_format_e = a & b
      inline def |(b: ssh_file_format_e): ssh_file_format_e = a | b
      inline def is(b: ssh_file_format_e): Boolean = (a & b) == b

  opaque type ssh_global_requests_e = CUnsignedInt
  object ssh_global_requests_e extends _BindgenEnumCUnsignedInt[ssh_global_requests_e]:
    given _tag: Tag[ssh_global_requests_e] = Tag.UInt
    inline def define(inline a: Long): ssh_global_requests_e = a.toUInt
    val SSH_GLOBAL_REQUEST_UNKNOWN = define(0)
    val SSH_GLOBAL_REQUEST_TCPIP_FORWARD = define(1)
    val SSH_GLOBAL_REQUEST_CANCEL_TCPIP_FORWARD = define(2)
    val SSH_GLOBAL_REQUEST_KEEPALIVE = define(3)
    val SSH_GLOBAL_REQUEST_NO_MORE_SESSIONS = define(4)
    inline def getName(inline value: ssh_global_requests_e): Option[String] =
      inline value match
        case SSH_GLOBAL_REQUEST_UNKNOWN => Some("SSH_GLOBAL_REQUEST_UNKNOWN")
        case SSH_GLOBAL_REQUEST_TCPIP_FORWARD => Some("SSH_GLOBAL_REQUEST_TCPIP_FORWARD")
        case SSH_GLOBAL_REQUEST_CANCEL_TCPIP_FORWARD => Some("SSH_GLOBAL_REQUEST_CANCEL_TCPIP_FORWARD")
        case SSH_GLOBAL_REQUEST_KEEPALIVE => Some("SSH_GLOBAL_REQUEST_KEEPALIVE")
        case SSH_GLOBAL_REQUEST_NO_MORE_SESSIONS => Some("SSH_GLOBAL_REQUEST_NO_MORE_SESSIONS")
        case _ => _root_.scala.None
    extension (a: ssh_global_requests_e)
      inline def &(b: ssh_global_requests_e): ssh_global_requests_e = a & b
      inline def |(b: ssh_global_requests_e): ssh_global_requests_e = a | b
      inline def is(b: ssh_global_requests_e): Boolean = (a & b) == b

  opaque type ssh_kex_types_e = CUnsignedInt
  object ssh_kex_types_e extends _BindgenEnumCUnsignedInt[ssh_kex_types_e]:
    given _tag: Tag[ssh_kex_types_e] = Tag.UInt
    inline def define(inline a: Long): ssh_kex_types_e = a.toUInt
    val SSH_KEX = define(0)
    val SSH_HOSTKEYS = define(1)
    val SSH_CRYPT_C_S = define(2)
    val SSH_CRYPT_S_C = define(3)
    val SSH_MAC_C_S = define(4)
    val SSH_MAC_S_C = define(5)
    val SSH_COMP_C_S = define(6)
    val SSH_COMP_S_C = define(7)
    val SSH_LANG_C_S = define(8)
    val SSH_LANG_S_C = define(9)
    inline def getName(inline value: ssh_kex_types_e): Option[String] =
      inline value match
        case SSH_KEX => Some("SSH_KEX")
        case SSH_HOSTKEYS => Some("SSH_HOSTKEYS")
        case SSH_CRYPT_C_S => Some("SSH_CRYPT_C_S")
        case SSH_CRYPT_S_C => Some("SSH_CRYPT_S_C")
        case SSH_MAC_C_S => Some("SSH_MAC_C_S")
        case SSH_MAC_S_C => Some("SSH_MAC_S_C")
        case SSH_COMP_C_S => Some("SSH_COMP_C_S")
        case SSH_COMP_S_C => Some("SSH_COMP_S_C")
        case SSH_LANG_C_S => Some("SSH_LANG_C_S")
        case SSH_LANG_S_C => Some("SSH_LANG_S_C")
        case _ => _root_.scala.None
    extension (a: ssh_kex_types_e)
      inline def &(b: ssh_kex_types_e): ssh_kex_types_e = a & b
      inline def |(b: ssh_kex_types_e): ssh_kex_types_e = a | b
      inline def is(b: ssh_kex_types_e): Boolean = (a & b) == b

  opaque type ssh_keycmp_e = CUnsignedInt
  object ssh_keycmp_e extends _BindgenEnumCUnsignedInt[ssh_keycmp_e]:
    given _tag: Tag[ssh_keycmp_e] = Tag.UInt
    inline def define(inline a: Long): ssh_keycmp_e = a.toUInt
    val SSH_KEY_CMP_PUBLIC = define(0)
    val SSH_KEY_CMP_PRIVATE = define(1)
    val SSH_KEY_CMP_CERTIFICATE = define(2)
    inline def getName(inline value: ssh_keycmp_e): Option[String] =
      inline value match
        case SSH_KEY_CMP_PUBLIC => Some("SSH_KEY_CMP_PUBLIC")
        case SSH_KEY_CMP_PRIVATE => Some("SSH_KEY_CMP_PRIVATE")
        case SSH_KEY_CMP_CERTIFICATE => Some("SSH_KEY_CMP_CERTIFICATE")
        case _ => _root_.scala.None
    extension (a: ssh_keycmp_e)
      inline def &(b: ssh_keycmp_e): ssh_keycmp_e = a & b
      inline def |(b: ssh_keycmp_e): ssh_keycmp_e = a | b
      inline def is(b: ssh_keycmp_e): Boolean = (a & b) == b

  opaque type ssh_keytypes_e = CUnsignedInt
  object ssh_keytypes_e extends _BindgenEnumCUnsignedInt[ssh_keytypes_e]:
    given _tag: Tag[ssh_keytypes_e] = Tag.UInt
    inline def define(inline a: Long): ssh_keytypes_e = a.toUInt
    val SSH_KEYTYPE_UNKNOWN = define(0)
    val SSH_KEYTYPE_DSS = define(1)
    val SSH_KEYTYPE_RSA = define(2)
    val SSH_KEYTYPE_RSA1 = define(3)
    val SSH_KEYTYPE_ECDSA = define(4)
    val SSH_KEYTYPE_ED25519 = define(5)
    val SSH_KEYTYPE_DSS_CERT01 = define(6)
    val SSH_KEYTYPE_RSA_CERT01 = define(7)
    val SSH_KEYTYPE_ECDSA_P256 = define(8)
    val SSH_KEYTYPE_ECDSA_P384 = define(9)
    val SSH_KEYTYPE_ECDSA_P521 = define(10)
    val SSH_KEYTYPE_ECDSA_P256_CERT01 = define(11)
    val SSH_KEYTYPE_ECDSA_P384_CERT01 = define(12)
    val SSH_KEYTYPE_ECDSA_P521_CERT01 = define(13)
    val SSH_KEYTYPE_ED25519_CERT01 = define(14)
    val SSH_KEYTYPE_SK_ECDSA = define(15)
    val SSH_KEYTYPE_SK_ECDSA_CERT01 = define(16)
    val SSH_KEYTYPE_SK_ED25519 = define(17)
    val SSH_KEYTYPE_SK_ED25519_CERT01 = define(18)
    inline def getName(inline value: ssh_keytypes_e): Option[String] =
      inline value match
        case SSH_KEYTYPE_UNKNOWN => Some("SSH_KEYTYPE_UNKNOWN")
        case SSH_KEYTYPE_DSS => Some("SSH_KEYTYPE_DSS")
        case SSH_KEYTYPE_RSA => Some("SSH_KEYTYPE_RSA")
        case SSH_KEYTYPE_RSA1 => Some("SSH_KEYTYPE_RSA1")
        case SSH_KEYTYPE_ECDSA => Some("SSH_KEYTYPE_ECDSA")
        case SSH_KEYTYPE_ED25519 => Some("SSH_KEYTYPE_ED25519")
        case SSH_KEYTYPE_DSS_CERT01 => Some("SSH_KEYTYPE_DSS_CERT01")
        case SSH_KEYTYPE_RSA_CERT01 => Some("SSH_KEYTYPE_RSA_CERT01")
        case SSH_KEYTYPE_ECDSA_P256 => Some("SSH_KEYTYPE_ECDSA_P256")
        case SSH_KEYTYPE_ECDSA_P384 => Some("SSH_KEYTYPE_ECDSA_P384")
        case SSH_KEYTYPE_ECDSA_P521 => Some("SSH_KEYTYPE_ECDSA_P521")
        case SSH_KEYTYPE_ECDSA_P256_CERT01 => Some("SSH_KEYTYPE_ECDSA_P256_CERT01")
        case SSH_KEYTYPE_ECDSA_P384_CERT01 => Some("SSH_KEYTYPE_ECDSA_P384_CERT01")
        case SSH_KEYTYPE_ECDSA_P521_CERT01 => Some("SSH_KEYTYPE_ECDSA_P521_CERT01")
        case SSH_KEYTYPE_ED25519_CERT01 => Some("SSH_KEYTYPE_ED25519_CERT01")
        case SSH_KEYTYPE_SK_ECDSA => Some("SSH_KEYTYPE_SK_ECDSA")
        case SSH_KEYTYPE_SK_ECDSA_CERT01 => Some("SSH_KEYTYPE_SK_ECDSA_CERT01")
        case SSH_KEYTYPE_SK_ED25519 => Some("SSH_KEYTYPE_SK_ED25519")
        case SSH_KEYTYPE_SK_ED25519_CERT01 => Some("SSH_KEYTYPE_SK_ED25519_CERT01")
        case _ => _root_.scala.None
    extension (a: ssh_keytypes_e)
      inline def &(b: ssh_keytypes_e): ssh_keytypes_e = a & b
      inline def |(b: ssh_keytypes_e): ssh_keytypes_e = a | b
      inline def is(b: ssh_keytypes_e): Boolean = (a & b) == b

  opaque type ssh_known_hosts_e = CInt
  object ssh_known_hosts_e extends _BindgenEnumCInt[ssh_known_hosts_e]:
    given _tag: Tag[ssh_known_hosts_e] = Tag.Int
    inline def define(inline a: CInt): ssh_known_hosts_e = a
    val SSH_KNOWN_HOSTS_ERROR = define(-2)
    val SSH_KNOWN_HOSTS_NOT_FOUND = define(-1)
    val SSH_KNOWN_HOSTS_UNKNOWN = define(0)
    val SSH_KNOWN_HOSTS_OK = define(1)
    val SSH_KNOWN_HOSTS_CHANGED = define(2)
    val SSH_KNOWN_HOSTS_OTHER = define(3)
    inline def getName(inline value: ssh_known_hosts_e): Option[String] =
      inline value match
        case SSH_KNOWN_HOSTS_ERROR => Some("SSH_KNOWN_HOSTS_ERROR")
        case SSH_KNOWN_HOSTS_NOT_FOUND => Some("SSH_KNOWN_HOSTS_NOT_FOUND")
        case SSH_KNOWN_HOSTS_UNKNOWN => Some("SSH_KNOWN_HOSTS_UNKNOWN")
        case SSH_KNOWN_HOSTS_OK => Some("SSH_KNOWN_HOSTS_OK")
        case SSH_KNOWN_HOSTS_CHANGED => Some("SSH_KNOWN_HOSTS_CHANGED")
        case SSH_KNOWN_HOSTS_OTHER => Some("SSH_KNOWN_HOSTS_OTHER")
        case _ => _root_.scala.None
    extension (a: ssh_known_hosts_e)
      inline def &(b: ssh_known_hosts_e): ssh_known_hosts_e = a & b
      inline def |(b: ssh_known_hosts_e): ssh_known_hosts_e = a | b
      inline def is(b: ssh_known_hosts_e): Boolean = (a & b) == b

  opaque type ssh_options_e = CUnsignedInt
  object ssh_options_e extends _BindgenEnumCUnsignedInt[ssh_options_e]:
    given _tag: Tag[ssh_options_e] = Tag.UInt
    inline def define(inline a: Long): ssh_options_e = a.toUInt
    val SSH_OPTIONS_HOST = define(0)
    val SSH_OPTIONS_PORT = define(1)
    val SSH_OPTIONS_PORT_STR = define(2)
    val SSH_OPTIONS_FD = define(3)
    val SSH_OPTIONS_USER = define(4)
    val SSH_OPTIONS_SSH_DIR = define(5)
    val SSH_OPTIONS_IDENTITY = define(6)
    val SSH_OPTIONS_ADD_IDENTITY = define(7)
    val SSH_OPTIONS_KNOWNHOSTS = define(8)
    val SSH_OPTIONS_TIMEOUT = define(9)
    val SSH_OPTIONS_TIMEOUT_USEC = define(10)
    val SSH_OPTIONS_SSH1 = define(11)
    val SSH_OPTIONS_SSH2 = define(12)
    val SSH_OPTIONS_LOG_VERBOSITY = define(13)
    val SSH_OPTIONS_LOG_VERBOSITY_STR = define(14)
    val SSH_OPTIONS_CIPHERS_C_S = define(15)
    val SSH_OPTIONS_CIPHERS_S_C = define(16)
    val SSH_OPTIONS_COMPRESSION_C_S = define(17)
    val SSH_OPTIONS_COMPRESSION_S_C = define(18)
    val SSH_OPTIONS_PROXYCOMMAND = define(19)
    val SSH_OPTIONS_BINDADDR = define(20)
    val SSH_OPTIONS_STRICTHOSTKEYCHECK = define(21)
    val SSH_OPTIONS_COMPRESSION = define(22)
    val SSH_OPTIONS_COMPRESSION_LEVEL = define(23)
    val SSH_OPTIONS_KEY_EXCHANGE = define(24)
    val SSH_OPTIONS_HOSTKEYS = define(25)
    val SSH_OPTIONS_GSSAPI_SERVER_IDENTITY = define(26)
    val SSH_OPTIONS_GSSAPI_CLIENT_IDENTITY = define(27)
    val SSH_OPTIONS_GSSAPI_DELEGATE_CREDENTIALS = define(28)
    val SSH_OPTIONS_HMAC_C_S = define(29)
    val SSH_OPTIONS_HMAC_S_C = define(30)
    val SSH_OPTIONS_PASSWORD_AUTH = define(31)
    val SSH_OPTIONS_PUBKEY_AUTH = define(32)
    val SSH_OPTIONS_KBDINT_AUTH = define(33)
    val SSH_OPTIONS_GSSAPI_AUTH = define(34)
    val SSH_OPTIONS_GLOBAL_KNOWNHOSTS = define(35)
    val SSH_OPTIONS_NODELAY = define(36)
    val SSH_OPTIONS_PUBLICKEY_ACCEPTED_TYPES = define(37)
    val SSH_OPTIONS_PROCESS_CONFIG = define(38)
    val SSH_OPTIONS_REKEY_DATA = define(39)
    val SSH_OPTIONS_REKEY_TIME = define(40)
    val SSH_OPTIONS_RSA_MIN_SIZE = define(41)
    val SSH_OPTIONS_IDENTITY_AGENT = define(42)
    val SSH_OPTIONS_IDENTITIES_ONLY = define(43)
    val SSH_OPTIONS_CONTROL_MASTER = define(44)
    val SSH_OPTIONS_CONTROL_PATH = define(45)
    val SSH_OPTIONS_CERTIFICATE = define(46)
    val SSH_OPTIONS_PROXYJUMP = define(47)
    val SSH_OPTIONS_PROXYJUMP_CB_LIST_APPEND = define(48)
    inline def getName(inline value: ssh_options_e): Option[String] =
      inline value match
        case SSH_OPTIONS_HOST => Some("SSH_OPTIONS_HOST")
        case SSH_OPTIONS_PORT => Some("SSH_OPTIONS_PORT")
        case SSH_OPTIONS_PORT_STR => Some("SSH_OPTIONS_PORT_STR")
        case SSH_OPTIONS_FD => Some("SSH_OPTIONS_FD")
        case SSH_OPTIONS_USER => Some("SSH_OPTIONS_USER")
        case SSH_OPTIONS_SSH_DIR => Some("SSH_OPTIONS_SSH_DIR")
        case SSH_OPTIONS_IDENTITY => Some("SSH_OPTIONS_IDENTITY")
        case SSH_OPTIONS_ADD_IDENTITY => Some("SSH_OPTIONS_ADD_IDENTITY")
        case SSH_OPTIONS_KNOWNHOSTS => Some("SSH_OPTIONS_KNOWNHOSTS")
        case SSH_OPTIONS_TIMEOUT => Some("SSH_OPTIONS_TIMEOUT")
        case SSH_OPTIONS_TIMEOUT_USEC => Some("SSH_OPTIONS_TIMEOUT_USEC")
        case SSH_OPTIONS_SSH1 => Some("SSH_OPTIONS_SSH1")
        case SSH_OPTIONS_SSH2 => Some("SSH_OPTIONS_SSH2")
        case SSH_OPTIONS_LOG_VERBOSITY => Some("SSH_OPTIONS_LOG_VERBOSITY")
        case SSH_OPTIONS_LOG_VERBOSITY_STR => Some("SSH_OPTIONS_LOG_VERBOSITY_STR")
        case SSH_OPTIONS_CIPHERS_C_S => Some("SSH_OPTIONS_CIPHERS_C_S")
        case SSH_OPTIONS_CIPHERS_S_C => Some("SSH_OPTIONS_CIPHERS_S_C")
        case SSH_OPTIONS_COMPRESSION_C_S => Some("SSH_OPTIONS_COMPRESSION_C_S")
        case SSH_OPTIONS_COMPRESSION_S_C => Some("SSH_OPTIONS_COMPRESSION_S_C")
        case SSH_OPTIONS_PROXYCOMMAND => Some("SSH_OPTIONS_PROXYCOMMAND")
        case SSH_OPTIONS_BINDADDR => Some("SSH_OPTIONS_BINDADDR")
        case SSH_OPTIONS_STRICTHOSTKEYCHECK => Some("SSH_OPTIONS_STRICTHOSTKEYCHECK")
        case SSH_OPTIONS_COMPRESSION => Some("SSH_OPTIONS_COMPRESSION")
        case SSH_OPTIONS_COMPRESSION_LEVEL => Some("SSH_OPTIONS_COMPRESSION_LEVEL")
        case SSH_OPTIONS_KEY_EXCHANGE => Some("SSH_OPTIONS_KEY_EXCHANGE")
        case SSH_OPTIONS_HOSTKEYS => Some("SSH_OPTIONS_HOSTKEYS")
        case SSH_OPTIONS_GSSAPI_SERVER_IDENTITY => Some("SSH_OPTIONS_GSSAPI_SERVER_IDENTITY")
        case SSH_OPTIONS_GSSAPI_CLIENT_IDENTITY => Some("SSH_OPTIONS_GSSAPI_CLIENT_IDENTITY")
        case SSH_OPTIONS_GSSAPI_DELEGATE_CREDENTIALS => Some("SSH_OPTIONS_GSSAPI_DELEGATE_CREDENTIALS")
        case SSH_OPTIONS_HMAC_C_S => Some("SSH_OPTIONS_HMAC_C_S")
        case SSH_OPTIONS_HMAC_S_C => Some("SSH_OPTIONS_HMAC_S_C")
        case SSH_OPTIONS_PASSWORD_AUTH => Some("SSH_OPTIONS_PASSWORD_AUTH")
        case SSH_OPTIONS_PUBKEY_AUTH => Some("SSH_OPTIONS_PUBKEY_AUTH")
        case SSH_OPTIONS_KBDINT_AUTH => Some("SSH_OPTIONS_KBDINT_AUTH")
        case SSH_OPTIONS_GSSAPI_AUTH => Some("SSH_OPTIONS_GSSAPI_AUTH")
        case SSH_OPTIONS_GLOBAL_KNOWNHOSTS => Some("SSH_OPTIONS_GLOBAL_KNOWNHOSTS")
        case SSH_OPTIONS_NODELAY => Some("SSH_OPTIONS_NODELAY")
        case SSH_OPTIONS_PUBLICKEY_ACCEPTED_TYPES => Some("SSH_OPTIONS_PUBLICKEY_ACCEPTED_TYPES")
        case SSH_OPTIONS_PROCESS_CONFIG => Some("SSH_OPTIONS_PROCESS_CONFIG")
        case SSH_OPTIONS_REKEY_DATA => Some("SSH_OPTIONS_REKEY_DATA")
        case SSH_OPTIONS_REKEY_TIME => Some("SSH_OPTIONS_REKEY_TIME")
        case SSH_OPTIONS_RSA_MIN_SIZE => Some("SSH_OPTIONS_RSA_MIN_SIZE")
        case SSH_OPTIONS_IDENTITY_AGENT => Some("SSH_OPTIONS_IDENTITY_AGENT")
        case SSH_OPTIONS_IDENTITIES_ONLY => Some("SSH_OPTIONS_IDENTITIES_ONLY")
        case SSH_OPTIONS_CONTROL_MASTER => Some("SSH_OPTIONS_CONTROL_MASTER")
        case SSH_OPTIONS_CONTROL_PATH => Some("SSH_OPTIONS_CONTROL_PATH")
        case SSH_OPTIONS_CERTIFICATE => Some("SSH_OPTIONS_CERTIFICATE")
        case SSH_OPTIONS_PROXYJUMP => Some("SSH_OPTIONS_PROXYJUMP")
        case SSH_OPTIONS_PROXYJUMP_CB_LIST_APPEND => Some("SSH_OPTIONS_PROXYJUMP_CB_LIST_APPEND")
        case _ => _root_.scala.None
    extension (a: ssh_options_e)
      inline def &(b: ssh_options_e): ssh_options_e = a & b
      inline def |(b: ssh_options_e): ssh_options_e = a | b
      inline def is(b: ssh_options_e): Boolean = (a & b) == b

  opaque type ssh_publickey_hash_type = CUnsignedInt
  object ssh_publickey_hash_type extends _BindgenEnumCUnsignedInt[ssh_publickey_hash_type]:
    given _tag: Tag[ssh_publickey_hash_type] = Tag.UInt
    inline def define(inline a: Long): ssh_publickey_hash_type = a.toUInt
    val SSH_PUBLICKEY_HASH_SHA1 = define(0)
    val SSH_PUBLICKEY_HASH_MD5 = define(1)
    val SSH_PUBLICKEY_HASH_SHA256 = define(2)
    inline def getName(inline value: ssh_publickey_hash_type): Option[String] =
      inline value match
        case SSH_PUBLICKEY_HASH_SHA1 => Some("SSH_PUBLICKEY_HASH_SHA1")
        case SSH_PUBLICKEY_HASH_MD5 => Some("SSH_PUBLICKEY_HASH_MD5")
        case SSH_PUBLICKEY_HASH_SHA256 => Some("SSH_PUBLICKEY_HASH_SHA256")
        case _ => _root_.scala.None
    extension (a: ssh_publickey_hash_type)
      inline def &(b: ssh_publickey_hash_type): ssh_publickey_hash_type = a & b
      inline def |(b: ssh_publickey_hash_type): ssh_publickey_hash_type = a | b
      inline def is(b: ssh_publickey_hash_type): Boolean = (a & b) == b

  opaque type ssh_publickey_state_e = CInt
  object ssh_publickey_state_e extends _BindgenEnumCInt[ssh_publickey_state_e]:
    given _tag: Tag[ssh_publickey_state_e] = Tag.Int
    inline def define(inline a: CInt): ssh_publickey_state_e = a
    val SSH_PUBLICKEY_STATE_ERROR = define(-1)
    val SSH_PUBLICKEY_STATE_NONE = define(0)
    val SSH_PUBLICKEY_STATE_VALID = define(1)
    val SSH_PUBLICKEY_STATE_WRONG = define(2)
    inline def getName(inline value: ssh_publickey_state_e): Option[String] =
      inline value match
        case SSH_PUBLICKEY_STATE_ERROR => Some("SSH_PUBLICKEY_STATE_ERROR")
        case SSH_PUBLICKEY_STATE_NONE => Some("SSH_PUBLICKEY_STATE_NONE")
        case SSH_PUBLICKEY_STATE_VALID => Some("SSH_PUBLICKEY_STATE_VALID")
        case SSH_PUBLICKEY_STATE_WRONG => Some("SSH_PUBLICKEY_STATE_WRONG")
        case _ => _root_.scala.None
    extension (a: ssh_publickey_state_e)
      inline def &(b: ssh_publickey_state_e): ssh_publickey_state_e = a & b
      inline def |(b: ssh_publickey_state_e): ssh_publickey_state_e = a | b
      inline def is(b: ssh_publickey_state_e): Boolean = (a & b) == b

  opaque type ssh_requests_e = CUnsignedInt
  object ssh_requests_e extends _BindgenEnumCUnsignedInt[ssh_requests_e]:
    given _tag: Tag[ssh_requests_e] = Tag.UInt
    inline def define(inline a: Long): ssh_requests_e = a.toUInt
    val SSH_REQUEST_AUTH = define(1)
    val SSH_REQUEST_CHANNEL_OPEN = define(2)
    val SSH_REQUEST_CHANNEL = define(3)
    val SSH_REQUEST_SERVICE = define(4)
    val SSH_REQUEST_GLOBAL = define(5)
    inline def getName(inline value: ssh_requests_e): Option[String] =
      inline value match
        case SSH_REQUEST_AUTH => Some("SSH_REQUEST_AUTH")
        case SSH_REQUEST_CHANNEL_OPEN => Some("SSH_REQUEST_CHANNEL_OPEN")
        case SSH_REQUEST_CHANNEL => Some("SSH_REQUEST_CHANNEL")
        case SSH_REQUEST_SERVICE => Some("SSH_REQUEST_SERVICE")
        case SSH_REQUEST_GLOBAL => Some("SSH_REQUEST_GLOBAL")
        case _ => _root_.scala.None
    extension (a: ssh_requests_e)
      inline def &(b: ssh_requests_e): ssh_requests_e = a & b
      inline def |(b: ssh_requests_e): ssh_requests_e = a | b
      inline def is(b: ssh_requests_e): Boolean = (a & b) == b

  opaque type ssh_scp_request_types = CUnsignedInt
  object ssh_scp_request_types extends _BindgenEnumCUnsignedInt[ssh_scp_request_types]:
    given _tag: Tag[ssh_scp_request_types] = Tag.UInt
    inline def define(inline a: Long): ssh_scp_request_types = a.toUInt
    val SSH_SCP_REQUEST_NEWDIR = define(1)
    val SSH_SCP_REQUEST_NEWFILE = define(2)
    val SSH_SCP_REQUEST_EOF = define(3)
    val SSH_SCP_REQUEST_ENDDIR = define(4)
    val SSH_SCP_REQUEST_WARNING = define(5)
    inline def getName(inline value: ssh_scp_request_types): Option[String] =
      inline value match
        case SSH_SCP_REQUEST_NEWDIR => Some("SSH_SCP_REQUEST_NEWDIR")
        case SSH_SCP_REQUEST_NEWFILE => Some("SSH_SCP_REQUEST_NEWFILE")
        case SSH_SCP_REQUEST_EOF => Some("SSH_SCP_REQUEST_EOF")
        case SSH_SCP_REQUEST_ENDDIR => Some("SSH_SCP_REQUEST_ENDDIR")
        case SSH_SCP_REQUEST_WARNING => Some("SSH_SCP_REQUEST_WARNING")
        case _ => _root_.scala.None
    extension (a: ssh_scp_request_types)
      inline def &(b: ssh_scp_request_types): ssh_scp_request_types = a & b
      inline def |(b: ssh_scp_request_types): ssh_scp_request_types = a | b
      inline def is(b: ssh_scp_request_types): Boolean = (a & b) == b

  opaque type ssh_server_known_e = CInt
  object ssh_server_known_e extends _BindgenEnumCInt[ssh_server_known_e]:
    given _tag: Tag[ssh_server_known_e] = Tag.Int
    inline def define(inline a: CInt): ssh_server_known_e = a
    val SSH_SERVER_ERROR = define(-1)
    val SSH_SERVER_NOT_KNOWN = define(0)
    val SSH_SERVER_KNOWN_OK = define(1)
    val SSH_SERVER_KNOWN_CHANGED = define(2)
    val SSH_SERVER_FOUND_OTHER = define(3)
    val SSH_SERVER_FILE_NOT_FOUND = define(4)
    inline def getName(inline value: ssh_server_known_e): Option[String] =
      inline value match
        case SSH_SERVER_ERROR => Some("SSH_SERVER_ERROR")
        case SSH_SERVER_NOT_KNOWN => Some("SSH_SERVER_NOT_KNOWN")
        case SSH_SERVER_KNOWN_OK => Some("SSH_SERVER_KNOWN_OK")
        case SSH_SERVER_KNOWN_CHANGED => Some("SSH_SERVER_KNOWN_CHANGED")
        case SSH_SERVER_FOUND_OTHER => Some("SSH_SERVER_FOUND_OTHER")
        case SSH_SERVER_FILE_NOT_FOUND => Some("SSH_SERVER_FILE_NOT_FOUND")
        case _ => _root_.scala.None
    extension (a: ssh_server_known_e)
      inline def &(b: ssh_server_known_e): ssh_server_known_e = a & b
      inline def |(b: ssh_server_known_e): ssh_server_known_e = a | b
      inline def is(b: ssh_server_known_e): Boolean = (a & b) == b

object aliases:
  import _root_.io.cloud4s.cli.bindings.ssh.aliases.*
  import _root_.io.cloud4s.cli.bindings.ssh.structs.*
  type fd_set = posix.sys.select.fd_set
  object fd_set:
    val _tag: Tag[fd_set] = summon[Tag[posix.sys.select.fd_set]]
    inline def apply(inline o: posix.sys.select.fd_set): fd_set = o
    extension (v: fd_set)
      inline def value: posix.sys.select.fd_set = v

  type mode_t = posix.sys.types.mode_t
  object mode_t:
    val _tag: Tag[mode_t] = summon[Tag[posix.sys.types.mode_t]]
    inline def apply(inline o: posix.sys.types.mode_t): mode_t = o
    extension (v: mode_t)
      inline def value: posix.sys.types.mode_t = v

  type size_t = libc.stddef.size_t
  object size_t:
    val _tag: Tag[size_t] = summon[Tag[libc.stddef.size_t]]
    inline def apply(inline o: libc.stddef.size_t): size_t = o
    extension (v: size_t)
      inline def value: libc.stddef.size_t = v

  opaque type socket_t = CInt
  object socket_t:
    given _tag: Tag[socket_t] = Tag.Int
    inline def apply(inline o: CInt): socket_t = o
    extension (v: socket_t)
      inline def value: CInt = v

  opaque type ssh_agent = Ptr[ssh_agent_struct]
  object ssh_agent:
    given _tag: Tag[ssh_agent] = Tag.Ptr[ssh_agent_struct](ssh_agent_struct._tag)
    inline def apply(inline o: Ptr[ssh_agent_struct]): ssh_agent = o
    extension (v: ssh_agent)
      inline def value: Ptr[ssh_agent_struct] = v

  /**
   * SSH authentication callback for password and publickey auth.
  */
  opaque type ssh_auth_callback = CFuncPtr6[CString, CString, size_t, CInt, CInt, Ptr[Byte], CInt]
  object ssh_auth_callback:
    given _tag: Tag[ssh_auth_callback] = Tag.materializeCFuncPtr6[CString, CString, size_t, CInt, CInt, Ptr[Byte], CInt]
    inline def fromPtr(ptr: Ptr[Byte] | CVoidPtr): ssh_auth_callback = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr6[CString, CString, size_t, CInt, CInt, Ptr[Byte], CInt]): ssh_auth_callback = o
    extension (v: ssh_auth_callback)
      inline def value: CFuncPtr6[CString, CString, size_t, CInt, CInt, Ptr[Byte], CInt] = v
      inline def toPtr: CVoidPtr = CFuncPtr.toPtr(v)

  opaque type ssh_buffer = Ptr[ssh_buffer_struct]
  object ssh_buffer:
    given _tag: Tag[ssh_buffer] = Tag.Ptr[ssh_buffer_struct](ssh_buffer_struct._tag)
    inline def apply(inline o: Ptr[ssh_buffer_struct]): ssh_buffer = o
    extension (v: ssh_buffer)
      inline def value: Ptr[ssh_buffer_struct] = v

  opaque type ssh_channel = Ptr[ssh_channel_struct]
  object ssh_channel:
    given _tag: Tag[ssh_channel] = Tag.Ptr[ssh_channel_struct](ssh_channel_struct._tag)
    inline def apply(inline o: Ptr[ssh_channel_struct]): ssh_channel = o
    extension (v: ssh_channel)
      inline def value: Ptr[ssh_channel_struct] = v

  opaque type ssh_connector = Ptr[ssh_connector_struct]
  object ssh_connector:
    given _tag: Tag[ssh_connector] = Tag.Ptr[ssh_connector_struct](ssh_connector_struct._tag)
    inline def apply(inline o: Ptr[ssh_connector_struct]): ssh_connector = o
    extension (v: ssh_connector)
      inline def value: Ptr[ssh_connector_struct] = v

  opaque type ssh_counter = Ptr[ssh_counter_struct]
  object ssh_counter:
    given _tag: Tag[ssh_counter] = Tag.Ptr[ssh_counter_struct](ssh_counter_struct._tag)
    inline def apply(inline o: Ptr[ssh_counter_struct]): ssh_counter = o
    extension (v: ssh_counter)
      inline def value: Ptr[ssh_counter_struct] = v

  opaque type ssh_event = Ptr[ssh_event_struct]
  object ssh_event:
    given _tag: Tag[ssh_event] = Tag.Ptr[ssh_event_struct](ssh_event_struct._tag)
    inline def apply(inline o: Ptr[ssh_event_struct]): ssh_event = o
    extension (v: ssh_event)
      inline def value: Ptr[ssh_event_struct] = v

  opaque type ssh_event_callback = CFuncPtr3[socket_t, CInt, Ptr[Byte], CInt]
  object ssh_event_callback:
    given _tag: Tag[ssh_event_callback] = Tag.materializeCFuncPtr3[socket_t, CInt, Ptr[Byte], CInt]
    inline def fromPtr(ptr: Ptr[Byte] | CVoidPtr): ssh_event_callback = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr3[socket_t, CInt, Ptr[Byte], CInt]): ssh_event_callback = o
    extension (v: ssh_event_callback)
      inline def value: CFuncPtr3[socket_t, CInt, Ptr[Byte], CInt] = v
      inline def toPtr: CVoidPtr = CFuncPtr.toPtr(v)

  opaque type ssh_gssapi_creds = Ptr[Byte]
  object ssh_gssapi_creds:
    given _tag: Tag[ssh_gssapi_creds] = Tag.Ptr(Tag.Byte)
    inline def apply(inline o: Ptr[Byte]): ssh_gssapi_creds = o
    extension (v: ssh_gssapi_creds)
      inline def value: Ptr[Byte] = v

  opaque type ssh_key = Ptr[ssh_key_struct]
  object ssh_key:
    given _tag: Tag[ssh_key] = Tag.Ptr[ssh_key_struct](ssh_key_struct._tag)
    inline def apply(inline o: Ptr[ssh_key_struct]): ssh_key = o
    extension (v: ssh_key)
      inline def value: Ptr[ssh_key_struct] = v

  opaque type ssh_message = Ptr[ssh_message_struct]
  object ssh_message:
    given _tag: Tag[ssh_message] = Tag.Ptr[ssh_message_struct](ssh_message_struct._tag)
    inline def apply(inline o: Ptr[ssh_message_struct]): ssh_message = o
    extension (v: ssh_message)
      inline def value: Ptr[ssh_message_struct] = v

  opaque type ssh_pcap_file = Ptr[ssh_pcap_file_struct]
  object ssh_pcap_file:
    given _tag: Tag[ssh_pcap_file] = Tag.Ptr[ssh_pcap_file_struct](ssh_pcap_file_struct._tag)
    inline def apply(inline o: Ptr[ssh_pcap_file_struct]): ssh_pcap_file = o
    extension (v: ssh_pcap_file)
      inline def value: Ptr[ssh_pcap_file_struct] = v

  opaque type ssh_scp = Ptr[ssh_scp_struct]
  object ssh_scp:
    given _tag: Tag[ssh_scp] = Tag.Ptr[ssh_scp_struct](ssh_scp_struct._tag)
    inline def apply(inline o: Ptr[ssh_scp_struct]): ssh_scp = o
    extension (v: ssh_scp)
      inline def value: Ptr[ssh_scp_struct] = v

  opaque type ssh_session = Ptr[ssh_session_struct]
  object ssh_session:
    given _tag: Tag[ssh_session] = Tag.Ptr[ssh_session_struct](ssh_session_struct._tag)
    inline def apply(inline o: Ptr[ssh_session_struct]): ssh_session = o
    extension (v: ssh_session)
      inline def value: Ptr[ssh_session_struct] = v

  opaque type ssh_string = Ptr[ssh_string_struct]
  object ssh_string:
    given _tag: Tag[ssh_string] = Tag.Ptr[ssh_string_struct](ssh_string_struct._tag)
    inline def apply(inline o: Ptr[ssh_string_struct]): ssh_string = o
    extension (v: ssh_string)
      inline def value: Ptr[ssh_string_struct] = v

  type timeval = posix.sys.time.timeval
  object timeval:
    val _tag: Tag[timeval] = summon[Tag[posix.sys.time.timeval]]
    inline def apply(inline o: posix.sys.time.timeval): timeval = o
    extension (v: timeval)
      inline def value: posix.sys.time.timeval = v

  type uint32_t = scala.scalanative.unsigned.UInt
  object uint32_t:
    val _tag: Tag[uint32_t] = summon[Tag[scala.scalanative.unsigned.UInt]]
    inline def apply(inline o: scala.scalanative.unsigned.UInt): uint32_t = o
    extension (v: uint32_t)
      inline def value: scala.scalanative.unsigned.UInt = v

  type uint64_t = scala.scalanative.unsigned.ULong
  object uint64_t:
    val _tag: Tag[uint64_t] = summon[Tag[scala.scalanative.unsigned.ULong]]
    inline def apply(inline o: scala.scalanative.unsigned.ULong): uint64_t = o
    extension (v: uint64_t)
      inline def value: scala.scalanative.unsigned.ULong = v

  type va_list = unsafe.CVarArgList
  object va_list:
    val _tag: Tag[va_list] = summon[Tag[unsafe.CVarArgList]]
    inline def apply(inline o: unsafe.CVarArgList): va_list = o
    extension (v: va_list)
      inline def value: unsafe.CVarArgList = v

object structs:
  import _root_.io.cloud4s.cli.bindings.ssh.aliases.*
  opaque type ssh_agent_struct = CStruct0
  object ssh_agent_struct:
    given _tag: Tag[ssh_agent_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_buffer_struct = CStruct0
  object ssh_buffer_struct:
    given _tag: Tag[ssh_buffer_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_channel_struct = CStruct0
  object ssh_channel_struct:
    given _tag: Tag[ssh_channel_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_connector_struct = CStruct0
  object ssh_connector_struct:
    given _tag: Tag[ssh_connector_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_counter_struct = CStruct4[uint64_t, uint64_t, uint64_t, uint64_t]
  object ssh_counter_struct:
    given _tag: Tag[ssh_counter_struct] = Tag.materializeCStruct4Tag[uint64_t, uint64_t, uint64_t, uint64_t]
    def apply()(using Zone): Ptr[ssh_counter_struct] = scala.scalanative.unsafe.alloc[ssh_counter_struct](1)
    def apply(in_bytes : uint64_t, out_bytes : uint64_t, in_packets : uint64_t, out_packets : uint64_t)(using Zone): Ptr[ssh_counter_struct] =
      val ____ptr = apply()
      (!____ptr).in_bytes = in_bytes
      (!____ptr).out_bytes = out_bytes
      (!____ptr).in_packets = in_packets
      (!____ptr).out_packets = out_packets
      ____ptr
    extension (struct: ssh_counter_struct)
      def in_bytes : uint64_t = struct._1
      def in_bytes_=(value: uint64_t): Unit = !struct.at1 = value
      def out_bytes : uint64_t = struct._2
      def out_bytes_=(value: uint64_t): Unit = !struct.at2 = value
      def in_packets : uint64_t = struct._3
      def in_packets_=(value: uint64_t): Unit = !struct.at3 = value
      def out_packets : uint64_t = struct._4
      def out_packets_=(value: uint64_t): Unit = !struct.at4 = value

  opaque type ssh_event_struct = CStruct0
  object ssh_event_struct:
    given _tag: Tag[ssh_event_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_key_struct = CStruct0
  object ssh_key_struct:
    given _tag: Tag[ssh_key_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_knownhosts_entry = CStruct4[CString, CString, ssh_key, CString]
  object ssh_knownhosts_entry:
    given _tag: Tag[ssh_knownhosts_entry] = Tag.materializeCStruct4Tag[CString, CString, ssh_key, CString]
    def apply()(using Zone): Ptr[ssh_knownhosts_entry] = scala.scalanative.unsafe.alloc[ssh_knownhosts_entry](1)
    def apply(hostname : CString, unparsed : CString, publickey : ssh_key, comment : CString)(using Zone): Ptr[ssh_knownhosts_entry] =
      val ____ptr = apply()
      (!____ptr).hostname = hostname
      (!____ptr).unparsed = unparsed
      (!____ptr).publickey = publickey
      (!____ptr).comment = comment
      ____ptr
    extension (struct: ssh_knownhosts_entry)
      def hostname : CString = struct._1
      def hostname_=(value: CString): Unit = !struct.at1 = value
      def unparsed : CString = struct._2
      def unparsed_=(value: CString): Unit = !struct.at2 = value
      def publickey : ssh_key = struct._3
      def publickey_=(value: ssh_key): Unit = !struct.at3 = value
      def comment : CString = struct._4
      def comment_=(value: CString): Unit = !struct.at4 = value

  opaque type ssh_message_struct = CStruct0
  object ssh_message_struct:
    given _tag: Tag[ssh_message_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_pcap_file_struct = CStruct0
  object ssh_pcap_file_struct:
    given _tag: Tag[ssh_pcap_file_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_scp_struct = CStruct0
  object ssh_scp_struct:
    given _tag: Tag[ssh_scp_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_session_struct = CStruct0
  object ssh_session_struct:
    given _tag: Tag[ssh_session_struct] = Tag.materializeCStruct0Tag

  opaque type ssh_string_struct = CStruct0
  object ssh_string_struct:
    given _tag: Tag[ssh_string_struct] = Tag.materializeCStruct0Tag

@link("ssh")

@extern
private[ssh] object extern_functions:
  import _root_.io.cloud4s.cli.bindings.ssh.enumerations.*
  import _root_.io.cloud4s.cli.bindings.ssh.aliases.*
  import _root_.io.cloud4s.cli.bindings.ssh.structs.*
  def _ssh_log(verbosity : CInt, function : CString, format : CString, rest: Any*): Unit = extern

  def ssh_basename(path : CString): CString = extern

  def ssh_blocking_flush(session : ssh_session, timeout : CInt): CInt = extern

  def ssh_buffer_add_data(buffer : ssh_buffer, data : Ptr[Byte], len : uint32_t): CInt = extern

  def ssh_buffer_free(buffer : ssh_buffer): Unit = extern

  def ssh_buffer_get(buffer : ssh_buffer): Ptr[Byte] = extern

  def ssh_buffer_get_data(buffer : ssh_buffer, data : Ptr[Byte], requestedlen : uint32_t): uint32_t = extern

  def ssh_buffer_get_len(buffer : ssh_buffer): uint32_t = extern

  def ssh_buffer_new(): ssh_buffer = extern

  def ssh_buffer_reinit(buffer : ssh_buffer): CInt = extern

  def ssh_channel_accept_forward(session : ssh_session, timeout_ms : CInt, destination_port : Ptr[CInt]): ssh_channel = extern

  def ssh_channel_accept_x11(channel : ssh_channel, timeout_ms : CInt): ssh_channel = extern

  def ssh_channel_cancel_forward(session : ssh_session, address : CString, port : CInt): CInt = extern

  def ssh_channel_change_pty_size(channel : ssh_channel, cols : CInt, rows : CInt): CInt = extern

  def ssh_channel_close(channel : ssh_channel): CInt = extern

  def ssh_channel_free(channel : ssh_channel): Unit = extern

  def ssh_channel_get_exit_state(channel : ssh_channel, pexit_code : Ptr[uint32_t], pexit_signal : Ptr[CString], pcore_dumped : Ptr[CInt]): CInt = extern

  def ssh_channel_get_exit_status(channel : ssh_channel): CInt = extern

  def ssh_channel_get_session(channel : ssh_channel): ssh_session = extern

  def ssh_channel_is_closed(channel : ssh_channel): CInt = extern

  def ssh_channel_is_eof(channel : ssh_channel): CInt = extern

  def ssh_channel_is_open(channel : ssh_channel): CInt = extern

  def ssh_channel_listen_forward(session : ssh_session, address : CString, port : CInt, bound_port : Ptr[CInt]): CInt = extern

  def ssh_channel_new(session : ssh_session): ssh_channel = extern

  def ssh_channel_open_auth_agent(channel : ssh_channel): CInt = extern

  def ssh_channel_open_forward(channel : ssh_channel, remotehost : CString, remoteport : CInt, sourcehost : CString, localport : CInt): CInt = extern

  def ssh_channel_open_forward_port(session : ssh_session, timeout_ms : CInt, destination_port : Ptr[CInt], originator : Ptr[CString], originator_port : Ptr[CInt]): ssh_channel = extern

  def ssh_channel_open_forward_unix(channel : ssh_channel, remotepath : CString, sourcehost : CString, localport : CInt): CInt = extern

  def ssh_channel_open_session(channel : ssh_channel): CInt = extern

  def ssh_channel_open_x11(channel : ssh_channel, orig_addr : CString, orig_port : CInt): CInt = extern

  def ssh_channel_poll(channel : ssh_channel, is_stderr : CInt): CInt = extern

  def ssh_channel_poll_timeout(channel : ssh_channel, timeout : CInt, is_stderr : CInt): CInt = extern

  def ssh_channel_read(channel : ssh_channel, dest : Ptr[Byte], count : uint32_t, is_stderr : CInt): CInt = extern

  def ssh_channel_read_nonblocking(channel : ssh_channel, dest : Ptr[Byte], count : uint32_t, is_stderr : CInt): CInt = extern

  def ssh_channel_read_timeout(channel : ssh_channel, dest : Ptr[Byte], count : uint32_t, is_stderr : CInt, timeout_ms : CInt): CInt = extern

  def ssh_channel_request_auth_agent(channel : ssh_channel): CInt = extern

  def ssh_channel_request_env(channel : ssh_channel, name : CString, value : CString): CInt = extern

  def ssh_channel_request_exec(channel : ssh_channel, cmd : CString): CInt = extern

  def ssh_channel_request_pty(channel : ssh_channel): CInt = extern

  def ssh_channel_request_pty_size(channel : ssh_channel, term : CString, cols : CInt, rows : CInt): CInt = extern

  def ssh_channel_request_pty_size_modes(channel : ssh_channel, term : CString, cols : CInt, rows : CInt, modes : Ptr[CUnsignedChar], modes_len : size_t): CInt = extern

  def ssh_channel_request_send_break(channel : ssh_channel, length : uint32_t): CInt = extern

  def ssh_channel_request_send_signal(channel : ssh_channel, signum : CString): CInt = extern

  def ssh_channel_request_sftp(channel : ssh_channel): CInt = extern

  def ssh_channel_request_shell(channel : ssh_channel): CInt = extern

  def ssh_channel_request_subsystem(channel : ssh_channel, subsystem : CString): CInt = extern

  def ssh_channel_request_x11(channel : ssh_channel, single_connection : CInt, protocol : CString, cookie : CString, screen_number : CInt): CInt = extern

  def ssh_channel_select(readchans : Ptr[ssh_channel], writechans : Ptr[ssh_channel], exceptchans : Ptr[ssh_channel], timeout : Ptr[timeval]): CInt = extern

  def ssh_channel_send_eof(channel : ssh_channel): CInt = extern

  def ssh_channel_set_blocking(channel : ssh_channel, blocking : CInt): Unit = extern

  def ssh_channel_set_counter(channel : ssh_channel, counter : ssh_counter): Unit = extern

  def ssh_channel_window_size(channel : ssh_channel): uint32_t = extern

  def ssh_channel_write(channel : ssh_channel, data : Ptr[Byte], len : uint32_t): CInt = extern

  def ssh_channel_write_stderr(channel : ssh_channel, data : Ptr[Byte], len : uint32_t): CInt = extern

  def ssh_clean_pubkey_hash(hash : Ptr[Ptr[CUnsignedChar]]): Unit = extern

  def ssh_connect(session : ssh_session): CInt = extern

  def ssh_connector_free(connector : ssh_connector): Unit = extern

  def ssh_connector_new(session : ssh_session): ssh_connector = extern

  def ssh_connector_set_in_channel(connector : ssh_connector, channel : ssh_channel, flags : ssh_connector_flags_e): CInt = extern

  def ssh_connector_set_in_fd(connector : ssh_connector, fd : socket_t): Unit = extern

  def ssh_connector_set_out_channel(connector : ssh_connector, channel : ssh_channel, flags : ssh_connector_flags_e): CInt = extern

  def ssh_connector_set_out_fd(connector : ssh_connector, fd : socket_t): Unit = extern

  def ssh_copyright(): CString = extern

  def ssh_dirname(path : CString): CString = extern

  def ssh_disconnect(session : ssh_session): Unit = extern

  def ssh_dump_knownhost(session : ssh_session): CString = extern

  def ssh_event_add_connector(event : ssh_event, connector : ssh_connector): CInt = extern

  def ssh_event_add_fd(event : ssh_event, fd : socket_t, events : CShort, cb : ssh_event_callback, userdata : Ptr[Byte]): CInt = extern

  def ssh_event_add_session(event : ssh_event, session : ssh_session): CInt = extern

  def ssh_event_dopoll(event : ssh_event, timeout : CInt): CInt = extern

  def ssh_event_free(event : ssh_event): Unit = extern

  def ssh_event_new(): ssh_event = extern

  def ssh_event_remove_connector(event : ssh_event, connector : ssh_connector): CInt = extern

  def ssh_event_remove_fd(event : ssh_event, fd : socket_t): CInt = extern

  def ssh_event_remove_session(event : ssh_event, session : ssh_session): CInt = extern

  def ssh_finalize(): CInt = extern

  def ssh_forward_accept(session : ssh_session, timeout_ms : CInt): ssh_channel = extern

  def ssh_forward_cancel(session : ssh_session, address : CString, port : CInt): CInt = extern

  def ssh_forward_listen(session : ssh_session, address : CString, port : CInt, bound_port : Ptr[CInt]): CInt = extern

  def ssh_free(session : ssh_session): Unit = extern

  def ssh_get_cipher_in(session : ssh_session): CString = extern

  def ssh_get_cipher_out(session : ssh_session): CString = extern

  def ssh_get_clientbanner(session : ssh_session): CString = extern

  def ssh_get_disconnect_message(session : ssh_session): CString = extern

  def ssh_get_error(error : Ptr[Byte]): CString = extern

  def ssh_get_error_code(error : Ptr[Byte]): CInt = extern

  def ssh_get_fd(session : ssh_session): socket_t = extern

  def ssh_get_fingerprint_hash(`type` : ssh_publickey_hash_type, hash : Ptr[CUnsignedChar], len : size_t): CString = extern

  def ssh_get_hexa(what : Ptr[CUnsignedChar], len : size_t): CString = extern

  def ssh_get_hmac_in(session : ssh_session): CString = extern

  def ssh_get_hmac_out(session : ssh_session): CString = extern

  def ssh_get_issue_banner(session : ssh_session): CString = extern

  def ssh_get_kex_algo(session : ssh_session): CString = extern

  def ssh_get_log_level(): CInt = extern

  def ssh_get_log_userdata(): Ptr[Byte] = extern

  def ssh_get_openssh_version(session : ssh_session): CInt = extern

  def ssh_get_poll_flags(session : ssh_session): CInt = extern

  def ssh_get_pubkey_hash(session : ssh_session, hash : Ptr[Ptr[CUnsignedChar]]): CInt = extern

  def ssh_get_publickey(session : ssh_session, key : Ptr[ssh_key]): CInt = extern

  def ssh_get_publickey_hash(key : ssh_key, `type` : ssh_publickey_hash_type, hash : Ptr[Ptr[CUnsignedChar]], hlen : Ptr[size_t]): CInt = extern

  def ssh_get_random(where : Ptr[Byte], len : CInt, strong : CInt): CInt = extern

  def ssh_get_server_publickey(session : ssh_session, key : Ptr[ssh_key]): CInt = extern

  def ssh_get_serverbanner(session : ssh_session): CString = extern

  def ssh_get_status(session : ssh_session): CInt = extern

  def ssh_get_version(session : ssh_session): CInt = extern

  def ssh_getpass(prompt : CString, buf : CString, len : size_t, echo : CInt, verify : CInt): CInt = extern

  def ssh_gssapi_set_creds(session : ssh_session, creds : ssh_gssapi_creds): Unit = extern

  def ssh_init(): CInt = extern

  def ssh_is_blocking(session : ssh_session): CInt = extern

  def ssh_is_connected(session : ssh_session): CInt = extern

  def ssh_is_server_known(session : ssh_session): CInt = extern

  def ssh_key_cmp(k1 : ssh_key, k2 : ssh_key, what : ssh_keycmp_e): CInt = extern

  def ssh_key_dup(key : ssh_key): ssh_key = extern

  def ssh_key_free(key : ssh_key): Unit = extern

  def ssh_key_is_private(k : ssh_key): CInt = extern

  def ssh_key_is_public(k : ssh_key): CInt = extern

  def ssh_key_new(): ssh_key = extern

  def ssh_key_type(key : ssh_key): ssh_keytypes_e = extern

  def ssh_key_type_from_name(name : CString): ssh_keytypes_e = extern

  def ssh_key_type_to_char(`type` : ssh_keytypes_e): CString = extern

  def ssh_known_hosts_parse_line(host : CString, line : CString, entry : Ptr[Ptr[ssh_knownhosts_entry]]): CInt = extern

  def ssh_knownhosts_entry_free(entry : Ptr[ssh_knownhosts_entry]): Unit = extern

  def ssh_log(session : ssh_session, prioriry : CInt, format : CString, rest: Any*): Unit = extern

  def ssh_message_channel_request_open_reply_accept(msg : ssh_message): ssh_channel = extern

  def ssh_message_channel_request_open_reply_accept_channel(msg : ssh_message, chan : ssh_channel): CInt = extern

  def ssh_message_channel_request_reply_success(msg : ssh_message): CInt = extern

  def ssh_message_free(msg : ssh_message): Unit = extern

  def ssh_message_get(session : ssh_session): ssh_message = extern

  def ssh_message_subtype(msg : ssh_message): CInt = extern

  def ssh_message_type(msg : ssh_message): CInt = extern

  def ssh_mkdir(pathname : CString, mode : mode_t): CInt = extern

  def ssh_new(): ssh_session = extern

  def ssh_options_copy(src : ssh_session, dest : Ptr[ssh_session]): CInt = extern

  def ssh_options_get(session : ssh_session, `type` : ssh_options_e, value : Ptr[CString]): CInt = extern

  def ssh_options_get_port(session : ssh_session, port_target : Ptr[CUnsignedInt]): CInt = extern

  def ssh_options_getopt(session : ssh_session, argcptr : Ptr[CInt], argv : Ptr[CString]): CInt = extern

  def ssh_options_parse_config(session : ssh_session, filename : CString): CInt = extern

  def ssh_options_set(session : ssh_session, `type` : ssh_options_e, value : Ptr[Byte]): CInt = extern

  def ssh_pcap_file_close(pcap : ssh_pcap_file): CInt = extern

  def ssh_pcap_file_free(pcap : ssh_pcap_file): Unit = extern

  def ssh_pcap_file_new(): ssh_pcap_file = extern

  def ssh_pcap_file_open(pcap : ssh_pcap_file, filename : CString): CInt = extern

  def ssh_pki_copy_cert_to_privkey(cert_key : ssh_key, privkey : ssh_key): CInt = extern

  def ssh_pki_export_privkey_base64(privkey : ssh_key, passphrase : CString, auth_fn : ssh_auth_callback, auth_data : Ptr[Byte], b64_key : Ptr[CString]): CInt = extern

  def ssh_pki_export_privkey_base64_format(privkey : ssh_key, passphrase : CString, auth_fn : ssh_auth_callback, auth_data : Ptr[Byte], b64_key : Ptr[CString], format : ssh_file_format_e): CInt = extern

  def ssh_pki_export_privkey_file(privkey : ssh_key, passphrase : CString, auth_fn : ssh_auth_callback, auth_data : Ptr[Byte], filename : CString): CInt = extern

  def ssh_pki_export_privkey_file_format(privkey : ssh_key, passphrase : CString, auth_fn : ssh_auth_callback, auth_data : Ptr[Byte], filename : CString, format : ssh_file_format_e): CInt = extern

  def ssh_pki_export_privkey_to_pubkey(privkey : ssh_key, pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_export_pubkey_base64(key : ssh_key, b64_key : Ptr[CString]): CInt = extern

  def ssh_pki_export_pubkey_file(key : ssh_key, filename : CString): CInt = extern

  def ssh_pki_generate(`type` : ssh_keytypes_e, parameter : CInt, pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_import_cert_base64(b64_cert : CString, `type` : ssh_keytypes_e, pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_import_cert_file(filename : CString, pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_import_privkey_base64(b64_key : CString, passphrase : CString, auth_fn : ssh_auth_callback, auth_data : Ptr[Byte], pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_import_privkey_file(filename : CString, passphrase : CString, auth_fn : ssh_auth_callback, auth_data : Ptr[Byte], pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_import_pubkey_base64(b64_key : CString, `type` : ssh_keytypes_e, pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_import_pubkey_file(filename : CString, pkey : Ptr[ssh_key]): CInt = extern

  def ssh_pki_key_ecdsa_name(key : ssh_key): CString = extern

  def ssh_print_hash(`type` : ssh_publickey_hash_type, hash : Ptr[CUnsignedChar], len : size_t): Unit = extern

  def ssh_print_hexa(descr : CString, what : Ptr[CUnsignedChar], len : size_t): Unit = extern

  def ssh_request_no_more_sessions(session : ssh_session): CInt = extern

  def ssh_scp_accept_request(scp : ssh_scp): CInt = extern

  def ssh_scp_close(scp : ssh_scp): CInt = extern

  def ssh_scp_deny_request(scp : ssh_scp, reason : CString): CInt = extern

  def ssh_scp_free(scp : ssh_scp): Unit = extern

  def ssh_scp_init(scp : ssh_scp): CInt = extern

  def ssh_scp_leave_directory(scp : ssh_scp): CInt = extern

  def ssh_scp_new(session : ssh_session, mode : CInt, location : CString): ssh_scp = extern

  def ssh_scp_pull_request(scp : ssh_scp): CInt = extern

  def ssh_scp_push_directory(scp : ssh_scp, dirname : CString, mode : CInt): CInt = extern

  def ssh_scp_push_file(scp : ssh_scp, filename : CString, size : size_t, perms : CInt): CInt = extern

  def ssh_scp_push_file64(scp : ssh_scp, filename : CString, size : uint64_t, perms : CInt): CInt = extern

  def ssh_scp_read(scp : ssh_scp, buffer : Ptr[Byte], size : size_t): CInt = extern

  def ssh_scp_request_get_filename(scp : ssh_scp): CString = extern

  def ssh_scp_request_get_permissions(scp : ssh_scp): CInt = extern

  def ssh_scp_request_get_size(scp : ssh_scp): size_t = extern

  def ssh_scp_request_get_size64(scp : ssh_scp): uint64_t = extern

  def ssh_scp_request_get_warning(scp : ssh_scp): CString = extern

  def ssh_scp_write(scp : ssh_scp, buffer : Ptr[Byte], len : size_t): CInt = extern

  def ssh_select(channels : Ptr[ssh_channel], outchannels : Ptr[ssh_channel], maxfd : socket_t, readfds : Ptr[fd_set], timeout : Ptr[timeval]): CInt = extern

  def ssh_send_debug(session : ssh_session, message : CString, always_display : CInt): CInt = extern

  def ssh_send_ignore(session : ssh_session, data : CString): CInt = extern

  def ssh_service_request(session : ssh_session, service : CString): CInt = extern

  def ssh_session_export_known_hosts_entry(session : ssh_session, pentry_string : Ptr[CString]): CInt = extern

  def ssh_session_get_known_hosts_entry(session : ssh_session, pentry : Ptr[Ptr[ssh_knownhosts_entry]]): ssh_known_hosts_e = extern

  def ssh_session_has_known_hosts_entry(session : ssh_session): ssh_known_hosts_e = extern

  def ssh_session_is_known_server(session : ssh_session): ssh_known_hosts_e = extern

  def ssh_session_set_disconnect_message(session : ssh_session, message : CString): CInt = extern

  def ssh_session_update_known_hosts(session : ssh_session): CInt = extern

  def ssh_set_agent_channel(session : ssh_session, channel : ssh_channel): CInt = extern

  def ssh_set_agent_socket(session : ssh_session, fd : socket_t): CInt = extern

  def ssh_set_blocking(session : ssh_session, blocking : CInt): Unit = extern

  def ssh_set_counters(session : ssh_session, scounter : ssh_counter, rcounter : ssh_counter): Unit = extern

  def ssh_set_fd_except(session : ssh_session): Unit = extern

  def ssh_set_fd_toread(session : ssh_session): Unit = extern

  def ssh_set_fd_towrite(session : ssh_session): Unit = extern

  def ssh_set_log_level(level : CInt): CInt = extern

  def ssh_set_log_userdata(data : Ptr[Byte]): CInt = extern

  def ssh_set_pcap_file(session : ssh_session, pcapfile : ssh_pcap_file): CInt = extern

  def ssh_silent_disconnect(session : ssh_session): Unit = extern

  def ssh_string_burn(str : ssh_string): Unit = extern

  def ssh_string_copy(str : ssh_string): ssh_string = extern

  def ssh_string_data(str : ssh_string): Ptr[Byte] = extern

  def ssh_string_fill(str : ssh_string, data : Ptr[Byte], len : size_t): CInt = extern

  def ssh_string_free(str : ssh_string): Unit = extern

  def ssh_string_free_char(s : CString): Unit = extern

  def ssh_string_from_char(what : CString): ssh_string = extern

  def ssh_string_get_char(str : ssh_string): CString = extern

  def ssh_string_len(str : ssh_string): size_t = extern

  def ssh_string_new(size : size_t): ssh_string = extern

  def ssh_string_to_char(str : ssh_string): CString = extern

  def ssh_userauth_agent(session : ssh_session, username : CString): CInt = extern

  def ssh_userauth_gssapi(session : ssh_session): CInt = extern

  def ssh_userauth_kbdint(session : ssh_session, user : CString, submethods : CString): CInt = extern

  def ssh_userauth_kbdint_getanswer(session : ssh_session, i : CUnsignedInt): CString = extern

  def ssh_userauth_kbdint_getinstruction(session : ssh_session): CString = extern

  def ssh_userauth_kbdint_getname(session : ssh_session): CString = extern

  def ssh_userauth_kbdint_getnanswers(session : ssh_session): CInt = extern

  def ssh_userauth_kbdint_getnprompts(session : ssh_session): CInt = extern

  def ssh_userauth_kbdint_getprompt(session : ssh_session, i : CUnsignedInt, echo : CString): CString = extern

  def ssh_userauth_kbdint_setanswer(session : ssh_session, i : CUnsignedInt, answer : CString): CInt = extern

  def ssh_userauth_list(session : ssh_session, username : CString): CInt = extern

  def ssh_userauth_none(session : ssh_session, username : CString): CInt = extern

  def ssh_userauth_password(session : ssh_session, username : CString, password : CString): CInt = extern

  def ssh_userauth_publickey(session : ssh_session, username : CString, privkey : ssh_key): CInt = extern

  def ssh_userauth_publickey_auto(session : ssh_session, username : CString, passphrase : CString): CInt = extern

  def ssh_userauth_publickey_auto_get_current_identity(session : ssh_session, value : Ptr[CString]): CInt = extern

  def ssh_userauth_try_publickey(session : ssh_session, username : CString, pubkey : ssh_key): CInt = extern

  def ssh_version(req_version : CInt): CString = extern

  def ssh_vlog(verbosity : CInt, function : CString, format : CString, va : Ptr[va_list]): Unit = extern

  def ssh_write_knownhost(session : ssh_session): CInt = extern


object functions:
  import _root_.io.cloud4s.cli.bindings.ssh.enumerations.*
  import _root_.io.cloud4s.cli.bindings.ssh.aliases.*
  export extern_functions.*

object constants:
  val SSH_LOG_NOLOG: CUnsignedInt = 0.toUInt
  val SSH_LOG_WARNING: CUnsignedInt = 1.toUInt
  val SSH_LOG_PROTOCOL: CUnsignedInt = 2.toUInt
  val SSH_LOG_PACKET: CUnsignedInt = 3.toUInt
  val SSH_LOG_FUNCTIONS: CUnsignedInt = 4.toUInt
  
  val SSH_SCP_WRITE: CUnsignedInt = 0.toUInt
  val SSH_SCP_READ: CUnsignedInt = 1.toUInt
  val SSH_SCP_RECURSIVE: CUnsignedInt = 16.toUInt
  
object types:
    export _root_.io.cloud4s.cli.bindings.ssh.structs.*
    export _root_.io.cloud4s.cli.bindings.ssh.aliases.*
    export _root_.io.cloud4s.cli.bindings.ssh.enumerations.*

object all:
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_auth_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_channel_requests_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_channel_type_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_connector_flags_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_control_master_options_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_error_types_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_file_format_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_global_requests_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_kex_types_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_keycmp_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_keytypes_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_known_hosts_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_options_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_publickey_hash_type
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_publickey_state_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_requests_e
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_scp_request_types
  export _root_.io.cloud4s.cli.bindings.ssh.enumerations.ssh_server_known_e
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.fd_set
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.mode_t
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.size_t
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.socket_t
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_agent
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_auth_callback
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_buffer
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_channel
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_connector
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_counter
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_event
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_event_callback
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_gssapi_creds
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_key
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_message
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_pcap_file
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_scp
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_session
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.ssh_string
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.timeval
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.uint32_t
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.uint64_t
  export _root_.io.cloud4s.cli.bindings.ssh.aliases.va_list
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_agent_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_buffer_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_channel_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_connector_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_counter_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_event_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_key_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_knownhosts_entry
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_message_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_pcap_file_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_scp_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_session_struct
  export _root_.io.cloud4s.cli.bindings.ssh.structs.ssh_string_struct
  export _root_.io.cloud4s.cli.bindings.ssh.functions._ssh_log
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_basename
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_blocking_flush
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_add_data
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_get
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_get_data
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_get_len
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_buffer_reinit
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_accept_forward
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_accept_x11
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_cancel_forward
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_change_pty_size
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_close
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_get_exit_state
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_get_exit_status
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_get_session
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_is_closed
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_is_eof
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_is_open
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_listen_forward
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_open_auth_agent
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_open_forward
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_open_forward_port
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_open_forward_unix
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_open_session
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_open_x11
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_poll
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_poll_timeout
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_read
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_read_nonblocking
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_read_timeout
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_auth_agent
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_env
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_exec
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_pty
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_pty_size
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_pty_size_modes
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_send_break
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_send_signal
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_sftp
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_shell
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_subsystem
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_request_x11
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_select
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_send_eof
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_set_blocking
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_set_counter
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_window_size
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_write
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_channel_write_stderr
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_clean_pubkey_hash
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connect
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connector_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connector_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connector_set_in_channel
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connector_set_in_fd
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connector_set_out_channel
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_connector_set_out_fd
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_copyright
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_dirname
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_disconnect
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_dump_knownhost
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_add_connector
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_add_fd
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_add_session
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_dopoll
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_remove_connector
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_remove_fd
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_event_remove_session
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_finalize
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_forward_accept
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_forward_cancel
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_forward_listen
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_cipher_in
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_cipher_out
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_clientbanner
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_disconnect_message
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_error
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_error_code
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_fd
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_fingerprint_hash
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_hexa
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_hmac_in
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_hmac_out
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_issue_banner
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_kex_algo
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_log_level
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_log_userdata
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_openssh_version
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_poll_flags
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_pubkey_hash
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_publickey
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_publickey_hash
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_random
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_server_publickey
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_serverbanner
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_status
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_get_version
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_getpass
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_gssapi_set_creds
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_init
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_is_blocking
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_is_connected
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_is_server_known
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_cmp
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_dup
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_is_private
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_is_public
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_type
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_type_from_name
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_key_type_to_char
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_known_hosts_parse_line
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_knownhosts_entry_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_log
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_channel_request_open_reply_accept
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_channel_request_open_reply_accept_channel
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_channel_request_reply_success
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_get
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_subtype
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_message_type
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_mkdir
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_options_copy
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_options_get
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_options_get_port
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_options_getopt
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_options_parse_config
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_options_set
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pcap_file_close
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pcap_file_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pcap_file_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pcap_file_open
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_copy_cert_to_privkey
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_privkey_base64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_privkey_base64_format
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_privkey_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_privkey_file_format
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_privkey_to_pubkey
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_pubkey_base64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_export_pubkey_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_generate
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_import_cert_base64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_import_cert_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_import_privkey_base64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_import_privkey_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_import_pubkey_base64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_import_pubkey_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_pki_key_ecdsa_name
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_print_hash
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_print_hexa
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_request_no_more_sessions
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_accept_request
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_close
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_deny_request
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_init
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_leave_directory
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_pull_request
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_push_directory
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_push_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_push_file64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_read
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_request_get_filename
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_request_get_permissions
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_request_get_size
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_request_get_size64
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_request_get_warning
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_scp_write
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_select
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_send_debug
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_send_ignore
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_service_request
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_session_export_known_hosts_entry
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_session_get_known_hosts_entry
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_session_has_known_hosts_entry
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_session_is_known_server
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_session_set_disconnect_message
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_session_update_known_hosts
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_agent_channel
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_agent_socket
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_blocking
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_counters
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_fd_except
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_fd_toread
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_fd_towrite
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_log_level
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_log_userdata
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_set_pcap_file
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_silent_disconnect
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_burn
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_copy
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_data
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_fill
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_free
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_free_char
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_from_char
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_get_char
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_len
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_new
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_string_to_char
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_agent
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_gssapi
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_getanswer
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_getinstruction
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_getname
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_getnanswers
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_getnprompts
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_getprompt
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_kbdint_setanswer
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_list
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_none
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_password
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_publickey
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_publickey_auto
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_publickey_auto_get_current_identity
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_userauth_try_publickey
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_version
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_vlog
  export _root_.io.cloud4s.cli.bindings.ssh.functions.ssh_write_knownhost
