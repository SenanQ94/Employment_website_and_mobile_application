using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web.Script.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WcfService1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWcfService1" in both code and config file together.
    [ServiceContract]
    public interface IWcfService1
    {


        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/SignUpCompany/?name={name}&phone={phone}&username={username}&password={password}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject SignUpCompany(string name, string phone, string username, string password);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/SignUpCandidate/?username={username}&password={password}&fullName={fullName}&phone={phone}&expYears={expYears}&expLevel={expLevel}&expField={expField}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject SignUpCandidate(string username, string password, string fullName, string phone, long expYears, string expLevel, string expField);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/login/?userType={userType}&username={username}&password={password}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject login(int userType, string username, string password);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/addJob/?userId={userId}&salary={salary}&title={title}&reqLevel={reqLevel}&reqEdu={reqEdu}&expYears={expYears}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject addJob(int userId, long salary, string title, string reqLevel, string reqEdu, int expYears);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/getAllJobs/?q={q}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject getAllJobs(int q);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/getspecJobs/?q={q}&userId={userId}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject getspecJobs(int q, int userId);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/getspecCandidate/?jobId={jobId}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject getspecCandidate(int jobId);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/submitJob/?jobId={jobId}&userId={userId}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject submitJob(int jobId, int userId);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/getDiploma/", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject getDiploma();

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/addDiploma/?title={title}&field={field}", BodyStyle = WebMessageBodyStyle.Bare, RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        ResultObject addDiploma(string title, string field);
        

    }

   }
