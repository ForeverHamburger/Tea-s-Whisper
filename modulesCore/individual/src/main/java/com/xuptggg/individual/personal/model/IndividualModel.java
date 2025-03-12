package com.xuptggg.individual.personal.model;


import com.xuptggg.individual.personal.contract.IIndividualContract;

public class IndividualModel implements IIndividualContract.IIndividualModel<String> {
    @Override
    public void execute(String data, LoadIndividualInfoCallBack callBack) {
        IndividualInfo info = new IndividualInfo();

        callBack.onSuccess(info);
    }
}
