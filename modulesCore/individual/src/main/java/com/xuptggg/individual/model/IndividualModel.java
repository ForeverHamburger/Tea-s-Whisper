package com.xuptggg.individual.model;

import com.xuptggg.individual.contract.IIndividualContract;

import java.util.List;

public class IndividualModel implements IIndividualContract.IIndividualModel<String> {
    @Override
    public void execute(String data, LoadIndividualInfoCallBack callBack) {
        IndividualInfo info = new IndividualInfo();

        callBack.onSuccess(info);
    }
}
