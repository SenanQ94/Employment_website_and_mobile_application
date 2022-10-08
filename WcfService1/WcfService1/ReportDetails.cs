using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1
{
    public class ReportDetails
    {
        public long userId { set; get; }
        public long userType { set; get; }
        public string username { set; get; }
        public long vehicleNumber { set; get; }
        public long cardNumber { set; get; }
        public long gasolineAmount { set; get; }
    }
}