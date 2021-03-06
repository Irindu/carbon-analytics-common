/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package org.wso2.carbon.analytics.common.data.provider.internal.rdbms;


import org.wso2.carbon.datasource.core.api.DataSourceService;
import org.wso2.carbon.datasource.core.exception.DataSourceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import javax.sql.DataSource;

/**
 * common functions to RDBMS connection and retrieval of data.
 */
public class RDBMSHelper {

    private static DataSourceService dataSourceService;

    /**
     * get connection object for the instance.
     *
     * @return java.sql.Connection object for the dataProvider Configuration
     */
    public static Connection getConnection(String dataSourceName)
            throws SQLException, DataSourceException {

        return ((DataSource) dataSourceService.getDataSource(dataSourceName)).getConnection();
    }


    /**
     * Get metadata type(linear,ordinal,time) for the given data type of the data base.
     *
     * @param dbType   String name of the database that the datatype belongs
     * @param dataType String data type name provided by the result set metadata
     * @return String metadata type
     */
    public static String getMetadataTypes(String dbType, String dataType) {
        String[] linearTypes = new String[0];
        String[] ordinalTypes = new String[0];
//        String[] timeTypes;
        //ToDo: data type implementation for supported databases
        switch (dbType) {
            case "mysql":
                linearTypes = new String[]{"INTEGER", "INT", "SMALLINT", "TINYINT", "MEDIUMINT", "BIGINT",
                        "DECIMAL", "NUMERIC", "FLOAT", "DOUBLE"};
                ordinalTypes = new String[]{"CHAR", "VARCHAR", "BINARY", "VARBINARY", "BLOB", "TEXT", "ENUM", "SET"};
                break;
            case "postgresql":
                break;
            case "derby":
                break;
            case "h2":
                linearTypes = new String[]{"INT", "INTEGER", "MEDIUMINT", "INT4", "SIGNED", "TINYINT", "SMALLINT",
                        "INT2", "YEAR", "BIGINT", "INT8", "IDENTITY", "DECIMAL", "NUMBER", "DEC", "NUMERIC",
                        "DOUBLE", "PRECISION",
                        "FLOAT", "FLOAT8", "REAL", "FLOAT4"};
                ordinalTypes = new String[]{"VARCHAR", "LONGVARCHAR", "VARCHAR2", "NVARCHAR", "NVARCHAR2",
                        "VARCHAR_CASESENSITIVE", "VARCHAR_IGNORECASE", "CHAR", "NCHAR", "CLOB", "TINYTEXT", "TEXT",
                        "MEDIUMTEXT", "LONGTEXT", "NTEXT", "NCLOB"};
                break;
            case "oracle":
                linearTypes = new String[]{"NUMBER", "BINARY_FLOAT", "BINARY_DOUBLE"};
                ordinalTypes = new String[]{"CHAR", "VARCHAR", "VARCHAR2", "NCHAR", "NVARCHAR2"};
                break;
            default:
                throw new NullPointerException("Database Not supported");
        }

        if (Arrays.asList(linearTypes).contains(dataType)) {
            return "linear";
        } else if (Arrays.asList(ordinalTypes).contains(dataType)) {
            return "ordinal";
        } else {
            return "time";
        }

    }

    public static void setDataSourceService(DataSourceService dataSourceService) {
        RDBMSHelper.dataSourceService = dataSourceService;
    }
}
