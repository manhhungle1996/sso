/**
 * Copyright © 2016-2023 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.com.atomi.openbanking.authservice.common.exception;


public class IncorrectParameterException extends Exception {
    static final long serialVersionUID = -7034897190745766939L;
    private String clientMessageId;
    private String transactionId;
    public IncorrectParameterException(String message) {
        super(message);
    }
    public IncorrectParameterException(String message, String clientMessageId, String transactionId) {
        super(message);
        this.clientMessageId = clientMessageId;
        this.transactionId = transactionId;
    }
    public String getClientMessageId() {
        return clientMessageId;
    }

    public String getTransactionId() {
        return transactionId;
    }
    public IncorrectParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
