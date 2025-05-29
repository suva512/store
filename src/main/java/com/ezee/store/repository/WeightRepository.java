package com.ezee.store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezee.store.config.JdbcConfig;
import com.ezee.store.dto.WeightDTO;
import com.ezee.store.exception.ErrorCode;
import com.ezee.store.exception.ServiceException;

@Repository
public class WeightRepository {
	@Autowired
	private JdbcConfig jdbcConfig;
	
	public static final Logger LOGGER = LogManager.getLogger("com.ezee.store.repository");
	
	public List<WeightDTO> fetchAllWeight() {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		Statement statement = null;
		List<WeightDTO> list = jdbcConfig.getList();
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			String query = "SELECT weight_id, weight_value, weight_unit, updated_at from weight";
			resultSet = statement.executeQuery(query);

			for (; resultSet.next();) {
				WeightDTO weightDto=new WeightDTO();
				weightDto.setWeightId(resultSet.getInt("weight_id"));
				weightDto.setWeightValue(resultSet.getDouble("weight_value"));
				weightDto.setWeightUnit(resultSet.getString("weight_unit"));
				weightDto.setUpdatedAt(resultSet.getDate("updated_at"));
				list.add(weightDto);
			}
			System.out.println("executed weight repo. fetchall");
			if (list.isEmpty()) {
				throw new ServiceException(ErrorCode.EMPTY_RESULT_DATA_EXCEPTION);
			}

		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}

		return list;
	}
	public WeightDTO fetchById(int id) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		WeightDTO weightDto = null;

			try {
				connection = dataSource.getConnection();

				String query = "SELECT weight_id, weight_value, weight_unit, updated_at from weight where  weight_id=?";
				
				statement = connection.prepareStatement(query);
				statement.setInt(1, id);

				resultSet = statement.executeQuery();
				

				if (resultSet != null) {
					if (resultSet.next()) {
						weightDto=new WeightDTO();
						weightDto.setWeightId(resultSet.getInt("weight_id"));
						weightDto.setWeightValue(resultSet.getDouble("weight_value"));
						weightDto.setWeightUnit(resultSet.getString("weight_unit"));
						weightDto.setUpdatedAt(resultSet.getDate("updated_at"));
						
					 
						
					}
						 else {
								throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
							}				
						}

						System.out.println("fetchbyId executed.");	
					} catch (SQLException e) {
						LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
					} finally {
						if (connection != null) {
							try {
								connection.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
						}

						if (statement != null) {
							try {
								statement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
						if (resultSet != null) {
							try {
								resultSet.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
					}
					
					return weightDto;
				}
	public int delete(int id) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int affected = 0;

		try {
			connection = dataSource.getConnection();
			String query = "DELETE FROM weight WHERE weight_id = ?";

			statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			affected = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.info("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return affected;
	}
	public int update(WeightDTO weightDto) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int executeUpdate = 0;
		
		try {
			connection = dataSource.getConnection();
			String query = "UPDATE weight SET weight_value=?, weight_unit=?, updated_at=?, updated_at=?  where weight_id=?";
			
			statement = connection.prepareStatement(query);
			statement.setInt(1, weightDto.getWeightId());
			statement.setDouble(2, weightDto.getWeightValue());
			statement.setString(3, weightDto.getWeightUnit());
			statement.setDate(4, weightDto.getUpdatedAt());
			statement.executeUpdate();
			System.out.println("update executed.");

		} catch (SQLException e) {
			LOGGER.info("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return executeUpdate;
		
	}
	public int addWeight(WeightDTO weightDto) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int executeUpdate = 0;

		try {
			connection = dataSource.getConnection();

			String query = "INSERT INTO weight (weight_id, weight_value, weight_unit, updated_at) VALUES ( ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, weightDto.getWeightId());
			statement.setDouble(2, weightDto.getWeightValue());
			statement.setString(3, weightDto.getWeightUnit());
			statement.setDate(4, weightDto.getUpdatedAt());
			executeUpdate = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return executeUpdate;
	}
}


