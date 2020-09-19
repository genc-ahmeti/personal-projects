/**
 * configuration constants
 */

// the config object
const Config = {

    DATABASE_CREDENTIALS: {
        password: '1234',
        user: 'root',
        host: 'localhost',
        port: '3306',
        // name of the scheme where all the tables, procedures are
        database: 'mydb'
    }
}

module.exports = Config;
