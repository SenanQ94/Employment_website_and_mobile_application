using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1
{
    public class GetCandidate
    {
        public long p_id { set; get; }
        public String full_name { set; get; }
        public String phone { set; get; }
        public long exp_years { set; get; }
        public String exp_lev { set; get; }
        public String edu_field { set; get; }

    }
}