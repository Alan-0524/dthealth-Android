package com.dthealth.callback;

import com.dthealth.dao.entity.Message;

public interface MessageResultInterface extends BaseInterface{
    void processMessage(Message message);
}
