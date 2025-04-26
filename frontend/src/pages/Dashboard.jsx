import { useAppContext } from '../context/AppContext'
import { motion } from 'framer-motion'
import { FiDollarSign, FiArrowUpRight, FiArrowDownRight, FiPieChart } from 'react-icons/fi'
import StatCard from '../components/dashboard/StatCard'
import RecentTransactions from '../components/dashboard/RecentTransactions'
import AccountsList from '../components/dashboard/AccountsList'
import IncomeExpenseChart from '../components/dashboard/IncomeExpenseChart'
import { mockMonthlyIncome, mockMonthlyExpenses } from '../data/mockData'

function Dashboard() {
  const { 
    transactions, 
    accounts, 
    totalIncome, 
    totalExpenses, 
    balance,
    currency 
  } = useAppContext()
  
  // Format currency values
  const formatCurrency = (value) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: currency,
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(value)
  }
  
  const pageVariants = {
    initial: { opacity: 0, y: 20 },
    animate: { 
      opacity: 1, 
      y: 0,
      transition: {
        duration: 0.5,
        staggerChildren: 0.1
      }
    }
  }
  
  const cardVariants = {
    initial: { opacity: 0, y: 20 },
    animate: { opacity: 1, y: 0 }
  }
  
  return (
    <motion.div
      variants={pageVariants}
      initial="initial"
      animate="animate"
    >
      <div className="md:flex md:items-center md:justify-between mb-8">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Financial Dashboard
          </h1>
          <p className="mt-1 text-sm text-neutral-500">
            A summary of your financial activity and account status
          </p>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          <button className="btn btn-primary">
            + Add Transaction
          </button>
        </div>
      </div>
      
      <motion.div 
        className="grid grid-cols-1 gap-6 lg:grid-cols-3"
        variants={cardVariants}
      >
        <StatCard 
          title="Current Balance" 
          value={formatCurrency(balance)}
          icon={FiArrowUpRight}
          change={3.2}
          color="text-primary-600"
        />
        <StatCard 
          title="Total Income" 
          value={formatCurrency(totalIncome)}
          icon={FiArrowUpRight}
          change={5.8}
          color="text-success-600"
        />
        <StatCard 
          title="Total Expenses" 
          value={formatCurrency(totalExpenses)}
          icon={FiArrowDownRight}
          change={-2.4}
          color="text-error-600"
        />
      </motion.div>
      
      <div className="mt-8 grid grid-cols-1 gap-6 lg:grid-cols-3">
        <motion.div 
          className="lg:col-span-2"
          variants={cardVariants}
        >
          <IncomeExpenseChart 
            incomeData={mockMonthlyIncome} 
            expenseData={mockMonthlyExpenses} 
          />
        </motion.div>
        
        <motion.div variants={cardVariants}>
          <AccountsList accounts={accounts} />
        </motion.div>
      </div>
      
      <motion.div 
        className="mt-8"
        variants={cardVariants}
      >
        <RecentTransactions transactions={transactions} />
      </motion.div>
    </motion.div>
  )
}

export default Dashboard