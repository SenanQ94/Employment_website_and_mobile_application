using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace WcfService1
{
    public class CreateCompany
    {
        public long userId { set; get; }
        public String name { set; get; }
        public String phone { set; get; }
        public String username { set; get; }
        public String password { set; get; }
    }
}