using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace WcfService1
{
    public class CreateJob
    {
        public long userId { set; get; }
        public String companyName { set; get; }
        public String jobTitle { set; get; }
        public String reqLevel { set; get; }
        public String reqField { set; get; }
        public int reqYears { set; get; }
        public long salary { set; get; }
        
    }

}
