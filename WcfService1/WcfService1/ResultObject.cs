using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace WcfService1
{
    [DataContract]
    [KnownType(typeof(CreateCompany))]
    [KnownType(typeof(GetCompany))]
    [KnownType(typeof(DiplomaDetails))]
    [KnownType(typeof(List<DiplomaDetails>))]
    [KnownType(typeof(CreateCandidate))]
    [KnownType(typeof(CreateJob))]
    [KnownType(typeof(List<GetAllJobs>))]
    [KnownType(typeof(List<GetCandidate>))]
    [KnownType(typeof(Exception))]
    [KnownType(typeof(JsonResult))]
    [KnownType(typeof(GetAllJobs))]
    [KnownType(typeof(GetCandidate))]
    public class ResultObject
    {
        [DataMember]
        public long code { set; get; }
        [DataMember]
        public string message { set; get; }
        [DataMember]
        public object data { set; get; }
    }
}