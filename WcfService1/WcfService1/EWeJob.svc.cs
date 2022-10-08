using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web.Script.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data;
using MySql.Data.MySqlClient;
namespace WcfService1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WcfService1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WcfService1.svc or WcfService1.svc.cs at the Solution Explorer and start debugging.
    public class WcfService1 : IWcfService1
    {

        public ResultObject SignUpCandidate(string username, string password, string fullName, string phone, long expYears, string expLevel, string expField)
        {
            long id;
            ResultObject resultObject = null;
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            List<JsonResult> l = new List<JsonResult>();
            MySqlConnection con = new MySqlConnection(connStr);

            if (username.ToLower().Equals("admin"))
            {
                resultObject = new ResultObject();
                resultObject.code = -1;
                resultObject.data = "";
                resultObject.message = "Error: You can not use 'admin' as username";
                return resultObject;
            }
            else
            {


                MySqlCommand comm = new MySqlCommand("insert into candidate (p_username, p_password, p_full_name, p_phone, p_exp_years, p_exp_lev, p_edu_field) values (@username, @password, @fullName, @phone, @expYears, @expLevel, @expField)", con);
                MySqlCommand comm1 = new MySqlCommand("select p_id from candidate where p_username = @username", con);
                try
                {
                    con.Open();
                    comm.Parameters.AddWithValue("@username", username);
                    comm.Parameters.AddWithValue("@password", password);
                    comm.Parameters.AddWithValue("@fullName", fullName);
                    comm.Parameters.AddWithValue("@phone", phone);
                    comm.Parameters.AddWithValue("@expYears", expYears);
                    comm.Parameters.AddWithValue("@expLevel", expLevel);
                    comm.Parameters.AddWithValue("@expField", expField);
                    comm1.Parameters.AddWithValue("@username", username);

                    int result = comm.ExecuteNonQuery();
                    if (result == 1)
                    {

                        resultObject = new ResultObject();
                        resultObject.code = result;
                        CreateCandidate newCandidate = new CreateCandidate();
                        newCandidate.fullName = fullName;
                        newCandidate.username = username;
                        newCandidate.password = password;
                        newCandidate.phone = phone;
                        newCandidate.expLevel = expLevel;
                        newCandidate.expField = expField;
                        newCandidate.expYears = expYears;
                        newCandidate.userId = (Int32)comm1.ExecuteScalar();
                        resultObject.data = newCandidate;
                        resultObject.message = "Success";

                    }
                    else
                    {
                        resultObject = new ResultObject();
                        resultObject.code = -1;
                        resultObject.data = null;
                        resultObject.message = "Error";
                    }
                    con.Close();
                    return resultObject;
                }
                catch (Exception e)
                {
                    resultObject = new ResultObject();
                    resultObject.code = -99;
                    resultObject.data = null;
                    resultObject.message = "Error: " + e.Message;
                    return resultObject;
                }
            }


        }

        public ResultObject SignUpCompany(string name, string phone, string username, string password)
        {
            long id;
            ResultObject resultObject = null;
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            List<JsonResult> l = new List<JsonResult>();
            MySqlConnection con = new MySqlConnection(connStr);

            if (username.ToLower().Equals("admin"))
            {
                resultObject = new ResultObject();
                resultObject.code = -1;
                resultObject.data = "";
                resultObject.message = "Error: You can not use 'admin' as username";
                return resultObject;
            }
            else{
                MySqlCommand comm = new MySqlCommand("insert into company (c_name, c_phone, c_username, c_password) values (@name, @phone, @username, @password)", con);
                MySqlCommand comm1 = new MySqlCommand("select c_id from company where c_username = @username", con);
                try
                {
                    con.Open();
                    comm.Parameters.AddWithValue("@name", name);
                    comm.Parameters.AddWithValue("@phone", phone);
                    comm.Parameters.AddWithValue("@username", username);
                    comm.Parameters.AddWithValue("@password", password);
                    comm1.Parameters.AddWithValue("@username", username);
                    int result = comm.ExecuteNonQuery();

                    if (result == 1)
                    {
                        resultObject = new ResultObject();
                        resultObject.code = 4;
                        CreateCompany newCompany = new CreateCompany();
                        newCompany.name = name;
                        newCompany.phone = phone;
                        newCompany.username = username;
                        newCompany.password = password;
                        newCompany.userId = (Int32)comm1.ExecuteScalar();


                        resultObject.data = newCompany;
                        resultObject.message = "Success";
                    }
                    else
                    {
                        resultObject = new ResultObject();
                        resultObject.code = -1;
                        resultObject.data = "";
                        resultObject.message = "Error";
                    }
                    con.Close();
                    return resultObject;
                }
                catch (Exception e)
                {
                    resultObject = new ResultObject();
                    resultObject.code = -99;
                    resultObject.data = "";
                    resultObject.message = "Error:" + e.Message;
                    return resultObject;
                }
            }


        }

        public ResultObject login(int userType, string username, string password)
        {
            ResultObject resultObject = null;
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            MySqlConnection con = new MySqlConnection(connStr);
            MySqlCommand comm;
            // 0 is candidate
            if (userType == 0)
            {
                comm = new MySqlCommand("SELECT p_id, p_full_name, p_phone, p_exp_years, p_exp_lev, p_edu_field FROM candidate WHERE p_username = @username AND p_password = @password ", con);
            }
            else
            {
                comm = new MySqlCommand("SELECT c_id, c_name, c_phone FROM company  WHERE c_username = @username AND c_password = @password ", con);
            }

            try
            {
                comm.Parameters.AddWithValue("@username", username);
                comm.Parameters.AddWithValue("@password", password);
                con.Open();


                MySqlDataReader reader = comm.ExecuteReader();
                if (reader.Read())
                {
                    resultObject = new ResultObject();
                    if (userType == 0)
                    {
                        GetCandidate can = new GetCandidate();
                        can.p_id = reader.GetInt64("p_id");
                        can.full_name = reader.GetString("p_full_name");
                        can.phone = reader.GetString("p_phone");
                        can.exp_lev= reader.GetString("p_exp_lev");
                        can.exp_years= reader.GetInt64("p_exp_years");      
                        can.edu_field = reader.GetString("p_edu_field");

                        resultObject.code = 1;
                        resultObject.message = "Success";
                        resultObject.data = can;
                    }
                    else
                    {
                        GetCompany comp = new GetCompany();
                        comp.c_id = reader.GetInt64("c_id");
                        comp.c_name = reader.GetString("c_name");
                        comp.c_phone = reader.GetString("c_phone");

                        resultObject.code = 1;
                        resultObject.message = "Success";
                        resultObject.data = comp;

                    }                   

                }
                else
                {
                    resultObject = new ResultObject();
                    resultObject.message = "wrong username or password";
                    resultObject.code = -1;
                    resultObject.data = "";
                }
                con.Close();
                return resultObject;
            }
            catch (Exception e)
            {
                resultObject = new ResultObject();
                resultObject.code = -99;
                resultObject.message = "ERROR: " + e.Message;
                resultObject.data = null;
                return resultObject;
            }
        }

        public ResultObject getAllJobs(int q)
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            List<GetAllJobs> allJobsList = new List<GetAllJobs>();
            GetAllJobs job;
            MySqlConnection con = new MySqlConnection(connStr);

            String sql;
            sql = "SELECT * FROM job ";
            // q=0 ==> expyears
            //q=1 ==> level
            if (q == 0)
            {
                sql += "ORDER BY job.j_req_exp_years ASC";
            }
            if (q == 1)
            {
                sql += "ORDER BY job.j_req_edu_lev ASC";

            }
            MySqlCommand comm = new MySqlCommand(sql, con);

            try
            {
                con.Open();
                MySqlDataReader reader = comm.ExecuteReader();
                while (reader.Read())
                {
                    job = new GetAllJobs();
                    job.comName = reader.GetString("j_co_name");
                    job.title = reader.GetString("j_title");
                    job.salary = reader.GetInt64("j_salary");
                    job.level = reader.GetString("j_req_edu_lev");
                    job.field = reader.GetString("j_edu_field");
                    job.years = reader.GetInt64("j_req_exp_years");
                    allJobsList.Add(job);
                    JavaScriptSerializer json = new JavaScriptSerializer();

                }
                resultObject = new ResultObject();
                resultObject.code = 1;
                resultObject.message = "Success";
                resultObject.data = allJobsList;
                con.Close();
                return resultObject;

            }
            catch (Exception e)
            {
                resultObject.code = -99;
                resultObject.message = "ERROR: " + e.Message;
                resultObject.data = "";
                return resultObject;
            }
        }

        public ResultObject getspecJobs(int q, int userId)
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            List<GetAllJobs> allJobsList = new List<GetAllJobs>();
            GetAllJobs job;
            MySqlConnection con = new MySqlConnection(connStr);

            String sql;
            sql = "SELECT job.* FROM job INNER JOIN candidate where (candidate.p_id = @userId) and ( job.j_req_edu_lev <= candidate.p_exp_lev) and (job.j_edu_field = candidate.p_edu_field) and (job.j_req_exp_years <= candidate.p_exp_years) ";
            // q=0 ==> expyears
            //q=1 ==> level
            if (q == 0)
            {
                sql += " ORDER BY job.j_req_exp_years ASC";
            }
            if (q == 1)
            {
                sql += " ORDER BY job.j_req_edu_lev ASC";

            }
            MySqlCommand comm = new MySqlCommand(sql, con);

            try
            {
                comm.Parameters.AddWithValue("@userId", userId);
                con.Open();
                MySqlDataReader reader = comm.ExecuteReader();
                while (reader.Read())
                {
                    job = new GetAllJobs();
                    job.comName = reader.GetString("j_co_name");
                    job.title = reader.GetString("j_title");
                    job.salary = reader.GetInt64("j_salary");
                    job.level = reader.GetString("j_req_edu_lev");
                    job.field = reader.GetString("j_edu_field");
                    job.years = reader.GetInt64("j_req_exp_years");
                    allJobsList.Add(job);
                    JavaScriptSerializer json = new JavaScriptSerializer();

                }
                resultObject = new ResultObject();
                resultObject.code = 1;
                resultObject.message = "Success";
                resultObject.data = allJobsList;
                con.Close();
                return resultObject;

            }
            catch (Exception e)
            {
                resultObject.code = -99;
                resultObject.message = "ERROR: " + e.Message;
                resultObject.data = "";
                return resultObject;
            }
        }

        public ResultObject getspecCandidate(int jobId)
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            List<GetCandidate> allCandidates = new List<GetCandidate>();
            GetCandidate candidate;
            MySqlConnection con = new MySqlConnection(connStr);

            String sql;
            //sql = "SELECT candidate.* FROM candidate INNER JOIN job  where (job.j_co_name =(select c_name from company where c_id =@userId)) and ( job.j_req_edu_lev <= candidate.p_exp_lev) and (job.j_edu_field = candidate.p_edu_field) and (job.j_req_exp_years <= candidate.p_exp_years) ";
            sql = "SELECT candidate.* FROM candidate INNER JOIN job  where (job.j_id =@jobId) and ( job.j_req_edu_lev <= candidate.p_exp_lev) and (job.j_edu_field = candidate.p_edu_field) and (job.j_req_exp_years <= candidate.p_exp_years) ";
            MySqlCommand comm = new MySqlCommand(sql, con);

            try
            {
                comm.Parameters.AddWithValue("@jobId", jobId);
                con.Open();
                MySqlDataReader reader = comm.ExecuteReader();
                while (reader.Read())
                {
                    candidate = new GetCandidate();
                    candidate.p_id = reader.GetInt64("p_id");
                    candidate.full_name = reader.GetString("p_full_name");
                    candidate.exp_years = reader.GetInt64("p_exp_years");
                    candidate.edu_field = reader.GetString("p_edu_field");
                    candidate.exp_lev = reader.GetString("p_exp_lev");
                    candidate.phone = reader.GetString("p_phone");
                    allCandidates.Add(candidate);
                    JavaScriptSerializer json = new JavaScriptSerializer();

                }
                resultObject = new ResultObject();
                resultObject.code = 1;
                resultObject.message = "Success";
                resultObject.data = allCandidates;
                con.Close();
                return resultObject;

            }
            catch (Exception e)
            {
                resultObject.code = -99;
                resultObject.message = "ERROR: " + e.Message;
                resultObject.data = null;
                return resultObject;
            }
        }

        public ResultObject submitJob(int jobId, int userId)
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";
            MySqlConnection con = new MySqlConnection(connStr);

            String sql;
            sql = "insert into cand_job (c_id, j_id) VALUES (@userId, @jobId) ";
           
            MySqlCommand comm = new MySqlCommand(sql, con);

            try
            {
                comm.Parameters.AddWithValue("@jobId", jobId);
                comm.Parameters.AddWithValue("@userId", userId);
                con.Open();
                int result = comm.ExecuteNonQuery();
                if (result == 1)
                {
                    resultObject = new ResultObject();
                    resultObject.code = result;                  
                    resultObject.data = "";
                    resultObject.message = "Success";
                    //resultObject.data = comm.LastInsertedId;
                }
                else
                {
                    resultObject = new ResultObject();
                    resultObject.code = -1;
                    resultObject.data = "";
                    resultObject.message = "Error";
                }
                con.Close();
                return resultObject;
            }
            catch (Exception e)
            {
                resultObject.code = -99;
                resultObject.message = "ERROR: "+e.Message;
                resultObject.data = null;
                return resultObject;
            }
        }

        public ResultObject addJob(int userId, long salary, string title, string reqLevel, string reqEdu, int expYears)
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";
            MySqlConnection con = new MySqlConnection(connStr);

            MySqlCommand comm; //(SELECT c_name FROM company WHERE c_id = @userId)
            String sql = "insert into job (j_co_name, j_title, j_salary, j_req_edu_lev, j_edu_field, j_req_exp_years) values ((SELECT c_name FROM company WHERE c_id = @userId), @title, @salary, @reqLevel, @reqEdu, @expYears)";

            comm = new MySqlCommand(sql, con);
            try
            {
                con.Open();
                comm.Parameters.AddWithValue("@userId", userId);
                comm.Parameters.AddWithValue("@title", title);
                comm.Parameters.AddWithValue("@salary", salary);     
                comm.Parameters.AddWithValue("@reqLevel", reqLevel);
                comm.Parameters.AddWithValue("@reqEdu", reqEdu);
                comm.Parameters.AddWithValue("@expYears", expYears);
                int result = comm.ExecuteNonQuery();
                if (result == 1)
                {
                    resultObject = new ResultObject();
                    resultObject.code = result;
                    //CreateUser newJob = new CreateUser();
                    //newJob.userId = comm.LastInsertedId;
                    resultObject.data = "";
                    resultObject.message = "Success";
                    //resultObject.data = comm.LastInsertedId;
                }
                else
                {
                    resultObject = new ResultObject();
                    resultObject.code = -1;
                    resultObject.data = "";
                    resultObject.message = "Error";
                }
                con.Close();
                return resultObject;
            }
            catch (Exception e)
            {
                resultObject = new ResultObject();
                resultObject.code = -99;
                resultObject.message = "error: " + e.Message;
                resultObject.data = null;
                return resultObject;
            }
        }

        public ResultObject getDiploma()
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";

            List<DiplomaDetails> diplomaslist = new List<DiplomaDetails>();
            DiplomaDetails diploma;
            MySqlConnection con = new MySqlConnection(connStr);

            String sql;
            sql = "SELECT d_title , d_field FROM diploma ORDER BY  d_title ";
            
            MySqlCommand comm = new MySqlCommand(sql, con);

            try
            {
                con.Open();
                MySqlDataReader reader = comm.ExecuteReader();
                while (reader.Read())
                {
                    diploma = new DiplomaDetails();                  
                    diploma.d_field = reader.GetString("d_field");
                    diploma.d_title = reader.GetString("d_title");
                    diplomaslist.Add(diploma);
                    JavaScriptSerializer json = new JavaScriptSerializer();

                }
                resultObject = new ResultObject();
                resultObject.code = 1;
                resultObject.message = "Success";
                resultObject.data = diplomaslist;
                con.Close();
                return resultObject;

            }
            catch (Exception e)
            {
                resultObject.code = -99;
                resultObject.message = "ERROR: " + e.Message;
                resultObject.data = "";
                return resultObject;
            }
        }

        public ResultObject addDiploma(string title, string field)
        {
            long id;
            ResultObject resultObject = new ResultObject();
            string connStr = "Server=MYSQL5030.site4now.net;Database=db_a69f54_sinanqa;Uid=a69f54_sinanqa;Pwd=Sen@115533#";
            MySqlConnection con = new MySqlConnection(connStr);
            MySqlCommand comm;
            String sql = "insert into diploma (d_title,d_field) values (@title, @field)";

            comm = new MySqlCommand(sql, con);
            try
            {
                con.Open();
                comm.Parameters.AddWithValue("@title", title);
                comm.Parameters.AddWithValue("@field", field);
                int result = comm.ExecuteNonQuery();
                if (result == 1)
                {
                    resultObject = new ResultObject();
                    resultObject.code = result;
                    resultObject.data = "";
                    resultObject.message = "Success";
                }
                else
                {
                    resultObject = new ResultObject();
                    resultObject.code = -1;
                    resultObject.data = "";
                    resultObject.message = "Error";
                }
                con.Close();
                return resultObject;
            }
            catch (Exception e)
            {
                resultObject = new ResultObject();
                resultObject.code = -99;
                resultObject.message = "error: " + e.Message;
                resultObject.data = null;
                return resultObject;
            }
        }


    }

    public class JsonResult
    {
        public long userId { set; get; }
    }

}