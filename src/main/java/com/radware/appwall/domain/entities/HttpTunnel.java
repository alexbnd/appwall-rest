package com.radware.appwall.domain.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TUNNEL", schema = "PUBLIC")
public class HttpTunnel {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @SerializedName("ID")
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WEB_SERVER_ID", referencedColumnName = "ID", nullable = true)
    private WebServerBinding webServer;

    @Expose
    @SerializedName("Name")
    @Column(name = "TUNNEL_NAME")
    public String name;

    @Expose
    @SerializedName("TunnelMode")
    @Column(name = "TUNNEL_MODE")
    public TunnelModeEnum tunnelMode;

    @Expose
    @SerializedName("ListenAddress")
    @Column(name = "LISTEN_ADDRESS")
    public String listenAddress;
    @Expose
    @SerializedName("ListenPort")
    @Column(name = "LISTEN_PORT")
    public Integer listenPort;
    @Expose
    @SerializedName("ForwardingAddress")
    @Column(name = "FORWARDING_ADDRESS")
    public String forwardingAddress;
    @Expose
    @SerializedName("IdleSession")
    @Column(name = "IDLE_SESSION")
    public Integer idleSession;
    @Expose
    @SerializedName("ProtectedType")
    @Column(name = "PROTECTED_TYPE")
    public ProtectedTypeEnum protectedType;
    @Expose
    @SerializedName("ServerName")
    @Column(name = "SERVER_NAME")
    public String serverName;
    @Expose
    @SerializedName("WebFarm")
    @Column(name = "WEB_FARM")
    public String webFarm;
    @Expose
    @SerializedName("ProtectedServer")
    @Column(name = "PROTECTED_SERVER")
    public String protectedServer;
    @Expose
    @SerializedName("Status")
    @Column(name = "STATUS")
    public StatusEnum status;
    @Expose
    @SerializedName("IncludeThisTunnel")
    @Column(name = "INCLUDE_THIS_TUNNEL")
    public Boolean includeThisTunnel;
    @Expose
    @SerializedName("ExternalSSLOffloading")
    @Column(name = "EXTERNAL_SSL_OFFLOADING")
    public Boolean externalSSLOffloading;
    @Expose
    @SerializedName("AutoTunnelSettingsOptimization")
    @Column(name = "AUTO_TUNNEL_SETTINGS_OPTIMIZATION")
    public Boolean autoTunnelSettingsOptimization;
    @Expose
    @SerializedName("TunnelThreadCount")
    @Column(name = "TUNNEL_THREAD_COUNT")
    public TunnelThreadCountEnum tunnelThreadCount;
    @Expose
    @SerializedName("OperationalMode")
    @Column(name = "OPERATIONAL_MODE")
    public OperationalModeEnum operationalMode;
    @Expose
    @SerializedName("FailureHandling")
    @Column(name = "FAILURE_HANDLING")
    public FailureHandlingEnum failureHandling;
    @Expose
    @SerializedName("IgnoreAutomatically")
    @Column(name = "IGNORE_AUTOMATICALLY")
    public Boolean ignoreAutomatically;
    @Expose
    @SerializedName("AutomaticlySuspendTunnel")
    @Column(name = "AUTOMATICALLY_SUSPEND_TUNNEL")
    public Boolean automaticlySuspendTunnel;
    @Expose
    @SerializedName("AfterConnection")
    @Column(name = "AFTER_CONNECTION")
    public String afterConnection;
    @Expose
    @SerializedName("PendingConnections")
    @Column(name = "PENDING_CONNECTIONS")
    public Integer pendingConnections;
    @Expose
    @SerializedName("ActiveConnections")
    @Column(name = "ACTIVE_CONNECTIONS")
    public Integer activeConnections;
    @Expose
    @SerializedName("XForwarded")
    @Column(name = "X_FORWARDED")
    public Boolean xForwarded;
    @Expose
    @SerializedName("allowHighAscii")
    @Column(name = "ALLOW_HIGH_ASCII")
    public Boolean allowHighAscii;
    @Expose
    @SerializedName("CodepageEncoding")
    @Column(name = "CODEPAGE_ENCODING")
    public Boolean codepageEncoding;
    @Expose
    @SerializedName("disablePersistent")
    @Column(name = "DISABLE_PERSISTENT")
    public Boolean disablePersistent;
    @Expose
    @SerializedName("AllowMessageParameterValues")
    @Column(name = "ALLOW_MESSAGE_PARAMETER_VALUES")
    public Boolean allowMessageParameterValues;
    @Expose
    @SerializedName("supportQuickClick")
    @Column(name = "SUPPORT_QUICK_CLICK")
    public Boolean supportQuickClick;
    @Expose
    @SerializedName("SessionDelimeter")
    @Column(name = "SESSION_DELIMITER")
    public String sessionDelimeter;
    @Expose
    @SerializedName("tcpHTTPTimeOutRadio")
    @Column(name = "TCP_HTTP_TIME_OUT_RADIO")
    public tcpHTTPTimeOutRadioEnum tcpHTTPTimeOutRadio;
    @Expose
    @SerializedName("ClientRequestDataWait")
    @Column(name = "CLIENT_REQUEST_DATA_WAIT")
    public Integer clientRequestDataWait;
    @Expose
    @SerializedName("ServerResponseDataWait")
    @Column(name = "SERVER_REQUEST_DATA_WAIT")
    public Integer serverResponseDataWait;
    @Expose
    @SerializedName("ClientRequestDataAfterResponseWait")
    @Column(name = "CLIENT_REQUEST_DATA_AFTER_RESPONSE_WAIT")
    public Integer clientRequestDataAfterResponseWait;
    @Expose
    @SerializedName("URLQueryDelimeter")
    @Column(name = "URL_QUERY_DELIMITER")
    public String uRLQueryDelimeter;
    @Expose
    @SerializedName("enablePercent3FCheck")
    @Column(name = "ENABLE_PERCENT_3F_CHECK")
    public Boolean enablePercent3FCheck;
    @Expose
    @SerializedName("ParameterDelimeter")
    @Column(name = "PARAMETER_DELIMITER")
    public String parameterDelimeter;
    @Expose
    @SerializedName("CodepageEncodingScheme")
    @Column(name = "CODEPAGE_ENCODING_SCHEME")
    public String codepageEncodingScheme;
    @Expose
    @SerializedName("supportParsingToNull")
    @Column(name = "SUPPORT_PARSING_TO_NULL")
    public Boolean supportParsingToNull;
    @Expose
    @SerializedName("allowDoubleSlashes")
    @Column(name = "ALLOW_DOUBLE_SLASHES")
    public Boolean allowDoubleSlashes;
    @Expose
    @SerializedName("purgeMultipleSlashes")
    @Column(name = "PURGE_MULTIPLE_SLASHES")
    public Boolean purgeMultipleSlashes;
    @Expose
    @SerializedName("analyzePathParameters")
    @Column(name = "ANALYZE_PATH_PARAMETERS")
    public Boolean analyzePathParameters;
    @Expose
    @SerializedName("analyzeCookies")
    @Column(name = "ANALYZE_COOKIES")
    public Boolean analyzeCookies;
    @Expose
    @SerializedName("allowNonRfc")
    @Column(name = "ALLOW_NON_RFC")
    public Boolean allowNonRfc;
    @Expose
    @SerializedName("parseMultiPart")
    @Column(name = "PURSE_MULTYPART")
    public Boolean parseMultiPart;
    @Expose
    @SerializedName("allowTrailing")
    @Column(name = "ALLOW_TRAILING")
    public Boolean allowTrailing;
    @Expose
    @SerializedName("useIis")
    @Column(name = "USE_IIS")
    public Boolean useIis;
    @Expose
    @SerializedName("allowExtraSpace")
    @Column(name = "ALLOW_EXTRA_SPACE")
    public Boolean allowExtraSpace;
    @Expose
    @SerializedName("EnableCustomHeaders")
    @Column(name = "ENABLE_CUSTOM_HEADERS")
    public Boolean enableCustomHeaders;
    @Expose
    @SerializedName("SignaturesEnable")
    @Column(name = "SIGNATURES_ENABLE")
    public Boolean signaturesEnable;
    @Expose
    @SerializedName("SignPerSM")
    @Column(name = "SIG_PER_SM")
    public SignPerSMEnum signPerSM;
    @Expose
    @SerializedName("SignatureHeaderName")
    @Column(name = "SIGNATURE_HEADER_NAME")
    public String signatureHeaderName;
    @Expose
    @SerializedName("SignatureMode")
    @Column(name = "SIGNATURE_MODE")
    public SignatureModeEnum signatureMode;
    @Expose
    @SerializedName("SignaturesPrivateKey")
    @Column(name = "SIGNATURES_PRIVATE_KEY")
    public String signaturesPrivateKey;
    @Expose
    @SerializedName("includeUrlCheck")
    @Column(name = "INCLUDE_URL_CHECK")
    public Boolean includeUrlCheck;
    @Expose
    @SerializedName("includeAppwallForwardingIpCheck")
    @Column(name = "INCLUDE_APPWALL_FORWARDING_IP_CHECK")
    public Boolean includeAppwallForwardingIpCheck;
    @Expose
    @SerializedName("includeAppwallForwardingPortCheck")
    @Column(name = "INCLUDE_APPWALL_FORWARDING_PORT_CHECK")
    public Boolean includeAppwallForwardingPortCheck;
    @Expose
    @SerializedName("includeDateTimeCheck")
    @Column(name = "INCLUDE_DATE_TIME_CHECK")
    public Boolean includeDateTimeCheck;
    @Expose
    @SerializedName("timeToLiveLb")
    @Column(name = "TIME_TO_LIVE_LB")
    public Integer timeToLiveLb;
    @Expose
    @SerializedName("headerNamesListCheck")
    @Column(name = "HEADER_NAME_LIST_CHECK")
    public Boolean headerNamesListCheck;
    @Expose
    @SerializedName("EnableProtectionSlowloris")
    @Column(name = "ENABLE_PROTECTION_SLOWLORIS")
    public Boolean enableProtectionSlowloris;
    @Expose
    @SerializedName("ChecksTimeGap")
    @Column(name = "CHECK_TIME_GAP")
    public Integer checksTimeGap;
    @Expose
    @SerializedName("MinimalSentDataAmount")
    @Column(name = "MINIMAL_SENT_DATA_AMOUNT")
    public Integer minimalSentDataAmount;
    @Expose
    @SerializedName("EnableCompressedRepliesSettings")
    @Column(name = "ENABLE_COMPRESSED_REPLIES_SETTINGS")
    public Boolean enableCompressedRepliesSettings;
    @Expose
    @SerializedName("SignPer")
    @Column(name = "SIGN_PER")
    public SignPerEnum signPer;
    @Expose
    @SerializedName("EnableServerIdentity")
    @Column(name = "ENABLE_SERVER_IDENTITY")
    public Boolean enableServerIdentity;
    @Expose
    @SerializedName("ServerIdentity")
    @Column(name = "SERVER_IDENTITY")
    public ServerIdentityEnum serverIdentity;
    @Expose
    @SerializedName("HeaderValues")
    @Column(name = "HEADER_VALUES")
    public String headerValues;
    @Expose
    @SerializedName("terminate100")
    @Column(name = "TERMINATE_100")
    public Boolean terminate100;
    @Expose
    @SerializedName("allowClientCache")
    @Column(name = "ALLOW_CLIENT_CACHE")
    public Boolean allowClientCache;
    @Expose
    @SerializedName("ClientStartLine")
    @Column(name = "CLIENT_START_LINE")
    public Integer clientStartLine;
    @Expose
    @SerializedName("ServerStartLine")
    @Column(name = "SERVER_START_LINE")
    public Integer serverStartLine;
    @Expose
    @SerializedName("ClientBody")
    @Column(name = "CLIENT_BODY")
    public Integer clientBody;
    @Expose
    @SerializedName("ServerBody")
    @Column(name = "SERVER_BODY")
    public Integer serverBody;
    @Expose
    @SerializedName("ClientTotalHeaders")
    @Column(name = "CLIENT_TOTAL_HEADERS")
    public Integer clientTotalHeaders;
    @Expose
    @SerializedName("ServerTotalHeaders")
    @Column(name = "SERVER_TOTAL_HEADERS")
    public Integer serverTotalHeaders;
    @Expose
    @SerializedName("ClientSingleHeader")
    @Column(name = "CLIENT_SINGLE_HEADER")
    public Integer clientSingleHeader;
    @Expose
    @SerializedName("ServerSingalHeader")
    @Column(name = "SERVER_SINGLE_HEADER")
    public Integer serverSingalHeader;

    public WebServerBinding getWebServer() {
        return webServer;
    }

    public void setWebServer(WebServerBinding webServer) {
        this.webServer = webServer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TunnelModeEnum getTunnelMode() {
        return tunnelMode;
    }

    public void setTunnelMode(TunnelModeEnum tunnelMode) {
        this.tunnelMode = tunnelMode;
    }

    public String getListenAddress() {
        return listenAddress;
    }

    public void setListenAddress(String listenAddress) {
        this.listenAddress = listenAddress;
    }

    public Integer getListenPort() {
        return listenPort;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    public String getForwardingAddress() {
        return forwardingAddress;
    }

    public void setForwardingAddress(String forwardingAddress) {
        this.forwardingAddress = forwardingAddress;
    }

    public Integer getIdleSession() {
        return idleSession;
    }

    public void setIdleSession(Integer idleSession) {
        this.idleSession = idleSession;
    }

    public ProtectedTypeEnum getProtectedType() {
        return protectedType;
    }

    public void setProtectedType(ProtectedTypeEnum protectedType) {
        this.protectedType = protectedType;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getWebFarm() {
        return webFarm;
    }

    public void setWebFarm(String webFarm) {
        this.webFarm = webFarm;
    }

    public String getProtectedServer() {
        return protectedServer;
    }

    public void setProtectedServer(String protectedServer) {
        this.protectedServer = protectedServer;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Boolean getIncludeThisTunnel() {
        return includeThisTunnel;
    }

    public void setIncludeThisTunnel(Boolean includeThisTunnel) {
        this.includeThisTunnel = includeThisTunnel;
    }

    public Boolean getExternalSSLOffloading() {
        return externalSSLOffloading;
    }

    public void setExternalSSLOffloading(Boolean externalSSLOffloading) {
        this.externalSSLOffloading = externalSSLOffloading;
    }

    public Boolean getAutoTunnelSettingsOptimization() {
        return autoTunnelSettingsOptimization;
    }

    public void setAutoTunnelSettingsOptimization(Boolean autoTunnelSettingsOptimization) {
        this.autoTunnelSettingsOptimization = autoTunnelSettingsOptimization;
    }

    public TunnelThreadCountEnum getTunnelThreadCount() {
        return tunnelThreadCount;
    }

    public void setTunnelThreadCount(TunnelThreadCountEnum tunnelThreadCount) {
        this.tunnelThreadCount = tunnelThreadCount;
    }

    public OperationalModeEnum getOperationalMode() {
        return operationalMode;
    }

    public void setOperationalMode(OperationalModeEnum operationalMode) {
        this.operationalMode = operationalMode;
    }

    public FailureHandlingEnum getFailureHandling() {
        return failureHandling;
    }

    public void setFailureHandling(FailureHandlingEnum failureHandling) {
        this.failureHandling = failureHandling;
    }

    public Boolean getIgnoreAutomatically() {
        return ignoreAutomatically;
    }

    public void setIgnoreAutomatically(Boolean ignoreAutomatically) {
        this.ignoreAutomatically = ignoreAutomatically;
    }

    public Boolean getAutomaticlySuspendTunnel() {
        return automaticlySuspendTunnel;
    }

    public void setAutomaticlySuspendTunnel(Boolean automaticlySuspendTunnel) {
        this.automaticlySuspendTunnel = automaticlySuspendTunnel;
    }

    public String getAfterConnection() {
        return afterConnection;
    }

    public void setAfterConnection(String afterConnection) {
        this.afterConnection = afterConnection;
    }

    public Integer getPendingConnections() {
        return pendingConnections;
    }

    public void setPendingConnections(Integer pendingConnections) {
        this.pendingConnections = pendingConnections;
    }

    public Integer getActiveConnections() {
        return activeConnections;
    }

    public void setActiveConnections(Integer activeConnections) {
        this.activeConnections = activeConnections;
    }

    public Boolean getxForwarded() {
        return xForwarded;
    }

    public void setxForwarded(Boolean xForwarded) {
        this.xForwarded = xForwarded;
    }

    public Boolean getAllowHighAscii() {
        return allowHighAscii;
    }

    public void setAllowHighAscii(Boolean allowHighAscii) {
        this.allowHighAscii = allowHighAscii;
    }

    public Boolean getCodepageEncoding() {
        return codepageEncoding;
    }

    public void setCodepageEncoding(Boolean codepageEncoding) {
        this.codepageEncoding = codepageEncoding;
    }

    public Boolean getDisablePersistent() {
        return disablePersistent;
    }

    public void setDisablePersistent(Boolean disablePersistent) {
        this.disablePersistent = disablePersistent;
    }

    public Boolean getAllowMessageParameterValues() {
        return allowMessageParameterValues;
    }

    public void setAllowMessageParameterValues(Boolean allowMessageParameterValues) {
        this.allowMessageParameterValues = allowMessageParameterValues;
    }

    public Boolean getSupportQuickClick() {
        return supportQuickClick;
    }

    public void setSupportQuickClick(Boolean supportQuickClick) {
        this.supportQuickClick = supportQuickClick;
    }

    public String getSessionDelimeter() {
        return sessionDelimeter;
    }

    public void setSessionDelimeter(String sessionDelimeter) {
        this.sessionDelimeter = sessionDelimeter;
    }

    public tcpHTTPTimeOutRadioEnum getTcpHTTPTimeOutRadio() {
        return tcpHTTPTimeOutRadio;
    }

    public void setTcpHTTPTimeOutRadio(tcpHTTPTimeOutRadioEnum tcpHTTPTimeOutRadio) {
        this.tcpHTTPTimeOutRadio = tcpHTTPTimeOutRadio;
    }

    public Integer getClientRequestDataWait() {
        return clientRequestDataWait;
    }

    public void setClientRequestDataWait(Integer clientRequestDataWait) {
        this.clientRequestDataWait = clientRequestDataWait;
    }

    public Integer getServerResponseDataWait() {
        return serverResponseDataWait;
    }

    public void setServerResponseDataWait(Integer serverResponseDataWait) {
        this.serverResponseDataWait = serverResponseDataWait;
    }

    public Integer getClientRequestDataAfterResponseWait() {
        return clientRequestDataAfterResponseWait;
    }

    public void setClientRequestDataAfterResponseWait(Integer clientRequestDataAfterResponseWait) {
        this.clientRequestDataAfterResponseWait = clientRequestDataAfterResponseWait;
    }

    public String getuRLQueryDelimeter() {
        return uRLQueryDelimeter;
    }

    public void setuRLQueryDelimeter(String uRLQueryDelimeter) {
        this.uRLQueryDelimeter = uRLQueryDelimeter;
    }

    public Boolean getEnablePercent3FCheck() {
        return enablePercent3FCheck;
    }

    public void setEnablePercent3FCheck(Boolean enablePercent3FCheck) {
        this.enablePercent3FCheck = enablePercent3FCheck;
    }

    public String getParameterDelimeter() {
        return parameterDelimeter;
    }

    public void setParameterDelimeter(String parameterDelimeter) {
        this.parameterDelimeter = parameterDelimeter;
    }

    public String getCodepageEncodingScheme() {
        return codepageEncodingScheme;
    }

    public void setCodepageEncodingScheme(String codepageEncodingScheme) {
        this.codepageEncodingScheme = codepageEncodingScheme;
    }

    public Boolean getSupportParsingToNull() {
        return supportParsingToNull;
    }

    public void setSupportParsingToNull(Boolean supportParsingToNull) {
        this.supportParsingToNull = supportParsingToNull;
    }

    public Boolean getAllowDoubleSlashes() {
        return allowDoubleSlashes;
    }

    public void setAllowDoubleSlashes(Boolean allowDoubleSlashes) {
        this.allowDoubleSlashes = allowDoubleSlashes;
    }

    public Boolean getPurgeMultipleSlashes() {
        return purgeMultipleSlashes;
    }

    public void setPurgeMultipleSlashes(Boolean purgeMultipleSlashes) {
        this.purgeMultipleSlashes = purgeMultipleSlashes;
    }

    public Boolean getAnalyzePathParameters() {
        return analyzePathParameters;
    }

    public void setAnalyzePathParameters(Boolean analyzePathParameters) {
        this.analyzePathParameters = analyzePathParameters;
    }

    public Boolean getAnalyzeCookies() {
        return analyzeCookies;
    }

    public void setAnalyzeCookies(Boolean analyzeCookies) {
        this.analyzeCookies = analyzeCookies;
    }

    public Boolean getAllowNonRfc() {
        return allowNonRfc;
    }

    public void setAllowNonRfc(Boolean allowNonRfc) {
        this.allowNonRfc = allowNonRfc;
    }

    public Boolean getParseMultiPart() {
        return parseMultiPart;
    }

    public void setParseMultiPart(Boolean parseMultiPart) {
        this.parseMultiPart = parseMultiPart;
    }

    public Boolean getAllowTrailing() {
        return allowTrailing;
    }

    public void setAllowTrailing(Boolean allowTrailing) {
        this.allowTrailing = allowTrailing;
    }

    public Boolean getUseIis() {
        return useIis;
    }

    public void setUseIis(Boolean useIis) {
        this.useIis = useIis;
    }

    public Boolean getAllowExtraSpace() {
        return allowExtraSpace;
    }

    public void setAllowExtraSpace(Boolean allowExtraSpace) {
        this.allowExtraSpace = allowExtraSpace;
    }

    public Boolean getEnableCustomHeaders() {
        return enableCustomHeaders;
    }

    public void setEnableCustomHeaders(Boolean enableCustomHeaders) {
        this.enableCustomHeaders = enableCustomHeaders;
    }

    public Boolean getSignaturesEnable() {
        return signaturesEnable;
    }

    public void setSignaturesEnable(Boolean signaturesEnable) {
        this.signaturesEnable = signaturesEnable;
    }

    public SignPerSMEnum getSignPerSM() {
        return signPerSM;
    }

    public void setSignPerSM(SignPerSMEnum signPerSM) {
        this.signPerSM = signPerSM;
    }

    public String getSignatureHeaderName() {
        return signatureHeaderName;
    }

    public void setSignatureHeaderName(String signatureHeaderName) {
        this.signatureHeaderName = signatureHeaderName;
    }

    public SignatureModeEnum getSignatureMode() {
        return signatureMode;
    }

    public void setSignatureMode(SignatureModeEnum signatureMode) {
        this.signatureMode = signatureMode;
    }

    public String getSignaturesPrivateKey() {
        return signaturesPrivateKey;
    }

    public void setSignaturesPrivateKey(String signaturesPrivateKey) {
        this.signaturesPrivateKey = signaturesPrivateKey;
    }

    public Boolean getIncludeUrlCheck() {
        return includeUrlCheck;
    }

    public void setIncludeUrlCheck(Boolean includeUrlCheck) {
        this.includeUrlCheck = includeUrlCheck;
    }

    public Boolean getIncludeAppwallForwardingIpCheck() {
        return includeAppwallForwardingIpCheck;
    }

    public void setIncludeAppwallForwardingIpCheck(Boolean includeAppwallForwardingIpCheck) {
        this.includeAppwallForwardingIpCheck = includeAppwallForwardingIpCheck;
    }

    public Boolean getIncludeAppwallForwardingPortCheck() {
        return includeAppwallForwardingPortCheck;
    }

    public void setIncludeAppwallForwardingPortCheck(Boolean includeAppwallForwardingPortCheck) {
        this.includeAppwallForwardingPortCheck = includeAppwallForwardingPortCheck;
    }

    public Boolean getIncludeDateTimeCheck() {
        return includeDateTimeCheck;
    }

    public void setIncludeDateTimeCheck(Boolean includeDateTimeCheck) {
        this.includeDateTimeCheck = includeDateTimeCheck;
    }

    public Integer getTimeToLiveLb() {
        return timeToLiveLb;
    }

    public void setTimeToLiveLb(Integer timeToLiveLb) {
        this.timeToLiveLb = timeToLiveLb;
    }

    public Boolean getHeaderNamesListCheck() {
        return headerNamesListCheck;
    }

    public void setHeaderNamesListCheck(Boolean headerNamesListCheck) {
        this.headerNamesListCheck = headerNamesListCheck;
    }

    public Boolean getEnableProtectionSlowloris() {
        return enableProtectionSlowloris;
    }

    public void setEnableProtectionSlowloris(Boolean enableProtectionSlowloris) {
        this.enableProtectionSlowloris = enableProtectionSlowloris;
    }

    public Integer getChecksTimeGap() {
        return checksTimeGap;
    }

    public void setChecksTimeGap(Integer checksTimeGap) {
        this.checksTimeGap = checksTimeGap;
    }

    public Integer getMinimalSentDataAmount() {
        return minimalSentDataAmount;
    }

    public void setMinimalSentDataAmount(Integer minimalSentDataAmount) {
        this.minimalSentDataAmount = minimalSentDataAmount;
    }

    public Boolean getEnableCompressedRepliesSettings() {
        return enableCompressedRepliesSettings;
    }

    public void setEnableCompressedRepliesSettings(Boolean enableCompressedRepliesSettings) {
        this.enableCompressedRepliesSettings = enableCompressedRepliesSettings;
    }

    public SignPerEnum getSignPer() {
        return signPer;
    }

    public void setSignPer(SignPerEnum signPer) {
        this.signPer = signPer;
    }

    public Boolean getEnableServerIdentity() {
        return enableServerIdentity;
    }

    public void setEnableServerIdentity(Boolean enableServerIdentity) {
        this.enableServerIdentity = enableServerIdentity;
    }

    public ServerIdentityEnum getServerIdentity() {
        return serverIdentity;
    }

    public void setServerIdentity(ServerIdentityEnum serverIdentity) {
        this.serverIdentity = serverIdentity;
    }

    public String getHeaderValues() {
        return headerValues;
    }

    public void setHeaderValues(String headerValues) {
        this.headerValues = headerValues;
    }

    public Boolean getTerminate100() {
        return terminate100;
    }

    public void setTerminate100(Boolean terminate100) {
        this.terminate100 = terminate100;
    }

    public Boolean getAllowClientCache() {
        return allowClientCache;
    }

    public void setAllowClientCache(Boolean allowClientCache) {
        this.allowClientCache = allowClientCache;
    }

    public Integer getClientStartLine() {
        return clientStartLine;
    }

    public void setClientStartLine(Integer clientStartLine) {
        this.clientStartLine = clientStartLine;
    }

    public Integer getServerStartLine() {
        return serverStartLine;
    }

    public void setServerStartLine(Integer serverStartLine) {
        this.serverStartLine = serverStartLine;
    }

    public Integer getClientBody() {
        return clientBody;
    }

    public void setClientBody(Integer clientBody) {
        this.clientBody = clientBody;
    }

    public Integer getServerBody() {
        return serverBody;
    }

    public void setServerBody(Integer serverBody) {
        this.serverBody = serverBody;
    }

    public Integer getClientTotalHeaders() {
        return clientTotalHeaders;
    }

    public void setClientTotalHeaders(Integer clientTotalHeaders) {
        this.clientTotalHeaders = clientTotalHeaders;
    }

    public Integer getServerTotalHeaders() {
        return serverTotalHeaders;
    }

    public void setServerTotalHeaders(Integer serverTotalHeaders) {
        this.serverTotalHeaders = serverTotalHeaders;
    }

    public Integer getClientSingleHeader() {
        return clientSingleHeader;
    }

    public void setClientSingleHeader(Integer clientSingleHeader) {
        this.clientSingleHeader = clientSingleHeader;
    }

    public Integer getServerSingalHeader() {
        return serverSingalHeader;
    }

    public void setServerSingalHeader(Integer serverSingalHeader) {
        this.serverSingalHeader = serverSingalHeader;
    }

    //TODO
    /*
    @Expose
    @SerializedName("GetUserIPFromHTTPHeader")
    @Column(name = "USER_IP")
    public List<FileTypeSecurityPojo> FileTypeSecurity;
    */

    public HttpTunnel() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

/*    public HttpTunnel(String Name, TunnelModeEnum TunnelMode, String ListenAddress, Integer ListenPort,
            String ForwardingAddress, Integer IdleSession, ProtectedTypeEnum ProtectedType) {
        this.Name = Name;
        this.TunnelMode = TunnelMode;
        this.ListenAddress = ListenAddress;
        this.ListenPort = ListenPort;
        this.ForwardingAddress = ForwardingAddress;
        this.IdleSession = IdleSession;
        this.ProtectedType = ProtectedType;
    }*/

/*    @Override
    public boolean equals(Object obj) {
        if ((obj == null)||(!(obj instanceof HttpTunnelsPojo))) {
            return false;
        }
        HttpTunnelsPojo other = ((HttpTunnelsPojo) obj);
        return (other.Name.equals(this.Name));
    }

    @Override
    public int hashCode() {
        return (Name.hashCode());
    }

    @Override
    public String toString() {
        return ("" +Name);
    }*/

    public enum FailureHandlingEnum {

        Change, Terminate;

    }

    public enum OperationalModeEnum {

        Active, Passive, Bypass;

    }

    public enum ProtectedTypeEnum {

        WebServer, WebFarm;

    }

    public enum ServerIdentityEnum {

        RemoveIdentity, ReplaceHeaderValue;

    }

    public enum SignPerEnum {

        forceCompressionRb, doNotCompressRb;

    }

    public enum SignPerSMEnum {

        SignPerSession, SignPerMessage;

    }

    public enum SignatureModeEnum {

        mode0, mode1, mode2;

    }

    public enum StatusEnum {

        Unknown, Bypass, Passive, Active;

    }

    public enum TunnelModeEnum {

        ReverseProxy, TransparentProxy;

    }

    public enum TunnelThreadCountEnum {

        Low, Medium, Hight;

    }

    public enum tcpHTTPTimeOutRadioEnum {

        TCPTimeout, HTTPTimeout;

    }

    public class FileTypeSecurityPojo {

        public String Extension;

    }



}
