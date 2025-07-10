package network.response;
public enum ApiKey {
    APIVERSION(18, 0, 4),
    DESCRIBETOPICPARTITIONS(75, 0, 0);
    public short api_key;
    public short min_sup_version;
    public short max_sup_version;
    ApiKey(int api_key, int min_version, int max_version) {
        this.api_key = (short)api_key;
        this.min_sup_version = (short)min_version;
        this.max_sup_version = (short)max_version;
    }
    public short GetApiKey() {
        return api_key;
    }
    public short GetMinVersion() {
        return min_sup_version;
    }
    public short GetMaxVersion() {
        return max_sup_version;
    }
}
