package com.forgerock.elasticsearch.changes;

/*
    Copyright 2015 ForgeRock AS

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/_changes")
public class WebSocketEndpoint {

    
    // TODO: add logers


    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        ChangesFeedPlugin.getRegister().registerListener(this);
    }

    public void sendMessage(String message) {
        session.getAsyncRemote().sendText(message);
    }

    public String getId() {
        return session == null ? null : session.getId();
    }

    @OnMessage
    public void onMessage(String message) {
    }

    @OnClose
    public void onClose(CloseReason reason) {
        ChangesFeedPlugin.getRegister().unregisterListener(this);
        this.session = null;
    }

    @OnError
    public void onError(Throwable t) {
    }


}
