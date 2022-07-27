package com.dbchain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import irita.sdk.client.RpcClient;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.constant.enums.BroadcastMode;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.model.*;
import irita.sdk.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class DBChainRPCClient extends RpcClient {
    private final String rpcUri;
    private String wsAddr;
    private final HttpUtils httpUtils;

    public DBChainRPCClient(ClientConfig clientConfig, OpbConfig opbConfig) {
        super(clientConfig, opbConfig);
        this.rpcUri = clientConfig.getRpcUri();
        this.wsAddr = clientConfig.getWsAddr();
        if (opbConfig != null && StringUtils.isNotEmpty(opbConfig.getProjectKey())) {
            this.httpUtils = new HttpUtils(opbConfig.getProjectKey());
        } else {
            this.httpUtils = new HttpUtils();
        }
    }

    public synchronized GasInfo simulateTx(byte[] txBytes) throws IOException {
        JsonRpc jsonRpc = JsonRpc.WrapAbciQuery(txBytes, "app/simulate");
        ObjectMapper mapper = new ObjectMapper();
        String str = this.httpUtils.post(this.rpcUri, mapper.writeValueAsString(jsonRpc));
        JsonRpcQueryResponse resp = (JsonRpcQueryResponse)mapper.readValue(str, JsonRpcQueryResponse.class);
        Objects.requireNonNull(resp, "use json deserialize json_rpc_response return null");
        String value = (String) Optional.of(resp).map(JsonRpcQueryResponse::getResult).map(ResultABCIQuery::getResponse).map(ResponseQuery::getValue).map((x) -> {
            return new String(Base64.getDecoder().decode(x));
        }).orElse("");
        if (StringUtils.isEmpty(value)) {
            throw new IritaSDKException(resp.getResult().getResponse().getLog());
        }
        Map maps = (Map) JSON.parse(value);
        String gas_used = (String)(((Map)(maps.get("gas_info"))).get("gas_used"));
        GasInfo gasInfo = new GasInfo();
        gasInfo.setGasUsed(gas_used);
        return gasInfo;
   }

    public synchronized ResultTx broadcastTx(byte[] txBytes, BroadcastMode mode) throws IOException {
        if (mode == null) {
            throw new IritaSDKException("unknown broadcastMode");
        } else {
            switch(mode) {
                case Sync:
                    return this.broadcastTxSync(txBytes);
                case Async:
                    return this.broadcastTxAsync(txBytes);
                case Commit:
                    return this.broadcastTxCommit(txBytes);
                default:
                    throw new IritaSDKException("unknown broadcastMode");
            }
        }
    }

    public synchronized ResultTx broadcastTxAsync(byte[] txBytes) throws IOException {
        JsonRpc jsonRpc = JsonRpc.WrapTxBytes(txBytes, "broadcast_tx_async");
        ObjectMapper mapper = new ObjectMapper();
        String str = this.httpUtils.post(this.rpcUri, mapper.writeValueAsString(jsonRpc));
        JSONObject object = JSON.parseObject(str);
        JSONObject resObj = object.getJSONObject("result");

        ResultTx resTx = new ResultTx();
        Result res = new Result();
        res.setHash(resObj.getString("hash"));
        resTx.setResult(res);
        return resTx;
    }

    private ResultTx checkResTxAndConvert(String res) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ResultTx resultTx = (ResultTx)mapper.readValue(res, ResultTx.class);
        if (resultTx.getError() != null) {
            throw new IritaSDKException(resultTx.getError().getData());
        } else if (resultTx.getCode() != 0) {
            throw new IritaSDKException(String.format("log: %s\nhash: %s", resultTx.getLog(), Optional.of(resultTx).map(ResultTx::getResult).map(Result::getHash).orElse("")));
        } else {
            return resultTx;
        }
    }
}
