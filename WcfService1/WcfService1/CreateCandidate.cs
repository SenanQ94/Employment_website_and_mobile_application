using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace WcfService1
{
    public class CreateCandidate
    {
        public long userId { set; get; }
        public String fullName { set; get; }
        public String phone { set; get; }
        public String username { set; get; }
        public String password { set; get; }
        public String expLevel { set; get; }
        public String expField { set; get; }
        public long expYears { set; get; }

    }
}